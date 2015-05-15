package jk.web.controllers.user;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.PropertyException;

import jk.web.HomeController;
import jk.web.controllers.management.NewMessageInformer;
import jk.web.entities.ContactEmailEntity;
import jk.web.entities.ContactEmailEntity.EmailStatus;
import jk.web.entities.ContactUsEntity;
import jk.web.entities.ContactUsEntity.ContactUsStatus;
import jk.web.entities.ReferenceNumberEntity;
import jk.web.entities.repositories.ContactEmailRepository;
import jk.web.entities.repositories.ContactUsRepository;
import jk.web.entities.repositories.ReferenceNumberRepository;
import jk.web.entities.user.ActivityEntity;
import jk.web.entities.user.AddressEntity;
import jk.web.entities.user.BusinessEntity;
import jk.web.entities.user.EMailEntity;
import jk.web.entities.user.EMailEntity.EMailStatus;
import jk.web.entities.user.LoginEntity;
import jk.web.entities.user.LoginEntity.Permission;
import jk.web.entities.user.TitleEntity;
import jk.web.entities.user.UserEntity;
import jk.web.entities.user.UserEntity.ActivityType;
import jk.web.filters.Statistic;
import jk.web.repositories.statictic.IpAddressRepository;
import jk.web.repositories.user.ActivityRepository;
import jk.web.repositories.user.TitleRepository;
import jk.web.user.Address.AddressStatus;
import jk.web.user.Address.AddressType;
import jk.web.user.Business;
import jk.web.user.User;
import jk.web.user.validators.SignUpFormValidator;
import jk.web.view.components.AddSiteForm;
import jk.web.view.components.ContactUsForm;
import jk.web.workers.UserWorker;
import jk.web.workers.email.EMailWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.context.Context;

@Controller
public class FormsController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private UserWorker userWorker;
	@Autowired
	private LocaleResolver localeResolver;
	@Autowired
	private EMailWorker eMailWorker;
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	@Autowired
	private UserDetailsService userDetailsService;
    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    private ActivityRepository activityRepository;

	@Autowired
	private TitleRepository titleRepository;

	@Value("${main.url}")
	private String mainURL;

	private static int year;
	private static List<String> yearsList;

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signup(User user, Model model){
		logger.entry(RequestMethod.GET);
		signupAttributes(model, titleRepository);
		model.addAttribute("user", new User());
		model.addAttribute("message", "home.welcome");
		return "home";
	}

	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(User user, BindingResult bindingResult, HttpServletRequest request, Model model, Context context) throws AddressException, MessagingException{

		signUpFormValidator.validate(user, bindingResult);

		if(bindingResult.hasErrors()){
			addUsernamePasswordRange(model);
			model.addAttribute("message", "home.welcome");
			return "home";
		}

		UserEntity newUser;
		try {
			newUser = userWorker.createNewUser(user, mainURL, localeResolver.resolveLocale(request), context);
		} catch (ParseException | NoSuchAlgorithmException e) {
			logger.catching(e);
			bindingResult.rejectValue("year", "SignUpFormValidator.this_field_must_be_filled");
			addUsernamePasswordRange(model);
			model.addAttribute("message", "home.welcome");
			return "home";
		}

		if(newUser.getId()!=null){
			authenticateUserAndSetSession( user.getUsername(), user.getNewPassword(), request);
			activityRepository.save(new ActivityEntity().setUserEntity(newUser).setType(ActivityType.NEW_USER).setUserid(newUser.getId()));
		}
		logger.trace("\n\t{}", newUser);

		model.addAttribute("title", "SignupController.signup.confirn.title");
		model.addAttribute("message", "SignupController.signup.confirn.title");
		model.addAttribute("contents", "SignupController.signup.confirn.sent");
		return "message";
	}

	@RequestMapping(value="/signup/business", method=RequestMethod.GET)
	public String signupBusiness(Business business, Model model){
		logger.entry(RequestMethod.GET);
		signupAttributes(model, titleRepository);

		return "signup_business";
	}

	@RequestMapping(value="/signup/business", method=RequestMethod.POST)
	public String signupBusiness(Business business, BindingResult bindingResult, HttpServletRequest request, Model model, Context context){
		logger.entry(business);

		signUpFormValidator.validate(business, bindingResult);

		if(bindingResult.hasErrors()){
			logger.trace("\n\tbindingResult.hasErrors() == true\n\t{}", bindingResult.getAllErrors());
			return "signup_business";
		}

		//save Login Entity
		LoginEntity le = userWorker.createNewLoginEntity(business.getUsername(), business.getNewPassword(), Permission.BUSINESS);

		if(le.getId()!=null){

			//save e-mail Entity
			userWorker.saveEMail(business.getEMail());

			//Fill User Entity
			UserEntity ue = userWorker.getUserEntity();
			ue.setFirstName(business.getFirstName());
			ue.setLastName(business.getLastName());
			userWorker.save();

			//Create Business Entity
			BusinessEntity be = new BusinessEntity();
			be.setCompanyName(business.getCompany());
			be.setCondition(business.getCondition());
			be.setCountryOfActivity(business.getCountryOfActivity());
			be.setSiteAddress(business.getSite());
			be.setVATnumber(business.getVatNumber());
			try {
				userWorker.saveBusinessEntity(be);
			} catch (PropertyException e) {
				logger.catching(e);
			}

			//create Address Entity
			AddressEntity ae = new AddressEntity();
			ae.setUserId(le.getId());
			String a = business.getAddress1();
			if(business.getAddress2()!=null)
				a += "\n"+business.getAddress2();
			ae.setAddress(a);
			ae.setCity(business.getCity());
			ae.setCountryCode(business.getCountry());
			ae.setPostalCode(business.getPostalcode());
			ae.setType(AddressType.WORK);
			ae.setStatus(AddressStatus.ACTIVE);
			userWorker.saveAddress(ae);

			logger.trace("\n\t{}\n\t{}\n\t{}", le, ue, ae);
		}

		return "message";
	}

	public void addUsernamePasswordRange(Model model) {
		model.addAttribute("usernameRange", signUpFormValidator.getUsernameRange());
		model.addAttribute("passwordRange", signUpFormValidator.getPasswordRange());
		signupAttributes(model, titleRepository);
	}

	private void authenticateUserAndSetSession(String username, String password, HttpServletRequest request) {
		logger.entry(username, password);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( username, password);

		// generate session if one doesn't exist
		request.getSession();

		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}

	@RequestMapping(value="/signup/clear")
	public String clear(User signUpForm, Model model){
		signUpForm.setUsername(null);
		signUpForm.setFirstName(null);
		signUpForm.setLastName(null);
		signUpForm.setSex(null);
		signUpForm.setBirthYear(null);
		signUpForm.setBirthMonth(null);
		signUpForm.setBirthDay(null);
		signUpForm.setEMail(null);
		return "signup";
	}

	@RequestMapping(value="/confirm/{userId}/{eMailId}/{passwordHashCode}")
	public String confirm(@PathVariable Long userId, @PathVariable Long eMailId, @PathVariable Integer passwordHashCode, Model model, Principal principal, HttpServletRequest request){
		logger.entry(userId, eMailId, passwordHashCode);

		String textCode;
		User user = new User();
		if(userId==null || eMailId==null || passwordHashCode==null)
			logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.error");
		else{
			LoginEntity loginEntity = userWorker.getLoginEntity(userId);
			if(loginEntity==null){

				logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.error");
				try { request.logout(); } catch (ServletException e) { logger.catching(e); }

			}else if(loginEntity.getPassword().hashCode()==passwordHashCode){

				user.setUsername(loginEntity.getUsername());

				String username = principal.getName();
				if(username!=null){
					LoginEntity le = userWorker.getLoginEntity(username);
					if(le.getId()!=loginEntity.getId())
						try { request.logout(); } catch (ServletException e) { logger.catching(e); }
				}

				EMailEntity em = getEmailById(loginEntity, eMailId);
				if(em!=null){
					if(em.getStatus()==EMailStatus.TO_CONFIRM){
						em.setStatus(EMailStatus.ACTIVE);
//						userWorker.saveEMail(em);
						Long permissions = loginEntity.getPermissions();
						logger.trace(permissions);
						if(permissions==null){
							loginEntity.setPermissions(Permission.DEFAULT.toLong());
							userWorker.save(loginEntity);
							logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.confirm");
						}else{
							userWorker.save(loginEntity);
							logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.confirmed");
						}
					}else
						logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.confirmed");
//					authenticateUserAndSetSession( user.getUsername(), user.getNewPassword(), request);
				}else
					logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.error");
			}else
				logger.error("\n\tpassword hash code({})!=@PathVariable({})\n\t{}",loginEntity.getPassword().hashCode(), passwordHashCode, textCode = "SignupController.confirm.error");
		}
		model.addAttribute(user);
		model.addAttribute("message", textCode);
		return "home";
	}

	private EMailEntity getEmailById(LoginEntity loginEntity, Long eMailId) {
		List<EMailEntity> emails = loginEntity.getEmails();
		EMailEntity emailEntity = null;
		for(EMailEntity ee:emails)
			if(ee.getId().equals(eMailId)){
				emailEntity = ee;
			}
		return emailEntity;
	}

	public static List<String> getYearsList() {
		Calendar now = Calendar.getInstance();
		int yearNow = now.get(Calendar.YEAR);
		if(yearNow!=year){
			year = yearNow;
			yearsList = new ArrayList<String>();

			for (int i = 0; i < 100; i++)
				yearsList.add(String.valueOf(year-i));
		}
		return logger.exit(yearsList);
	}

	public static void signupAttributes(Model model, TitleRepository titleRepository) {
		List<TitleEntity> findAll = titleRepository.findAll(new Sort("id"));
		logger.trace("\n\t{}", findAll);
		model.addAttribute("titles", findAll);
	}

	public enum MenuSelection{
		CONTACT_US,			//contact us page
		ADD_URL_ADDRESS,	
		CONFIRM_MESSAGE,	//confirmation message
		MANAGEMENT_STATISTIC,
		MANAGEMENT_USERS,
		MANAGEMENT_EDIT_CATEGORIES,
		MANAGEMENT_MESSAGES
	}
	@Autowired
	private HomeController homeController;
	@Autowired
	private IpAddressRepository ipAddressRepository;
	@Autowired
	private ContactEmailRepository contactEmailRepository;
	@Autowired
	private ReferenceNumberRepository referenceNumberRepository;
	@Autowired
	private ContactUsRepository contactUsRepository;
	@Autowired
	private NewMessageInformer newMessageInformer;
	//Values from application.properties file
	@Value("${jk.email.from}")
	private String emaleFrom;

	@RequestMapping(value="contact_us")
	public String contactUs(@Valid ContactUsForm contactUsForm, BindingResult bindingResult, @RequestParam(required=false) String contactUs, AddSiteForm addSiteForm, Model model, HttpServletRequest request){
		logger.entry(contactUsForm, contactUs);

		if (contactUs!=null && !bindingResult.hasErrors()){

			model.addAttribute("messageTitle", "contact_us_confirmation");
			model.addAttribute("content", new String[]{"thank_you", "all_the_best"});

			contactUs(contactUsForm, request);

			return returnToForms("Confirmation", MenuSelection.CONFIRM_MESSAGE, model);
		}

		return returnToForms("contact_us", MenuSelection.CONTACT_US, model);
	}

	private void contactUs(ContactUsForm contactUsForm, HttpServletRequest request) {
		ContactEmailEntity emailEntity = contactEmailRepository.findOneByEmail(contactUsForm.getEmail());
		if(emailEntity==null)
			emailEntity = contactEmailRepository.save(new ContactEmailEntity(contactUsForm.getEmail(), EmailStatus.TO_CONTACT));

		ReferenceNumberEntity referenceNumberEntity = referenceNumberRepository.findOneByReferenceNumber(contactUsForm.getReferenceNumber());
		if(referenceNumberEntity==null)
			referenceNumberEntity = referenceNumberRepository.save(new ReferenceNumberEntity(contactUsForm.getReferenceNumber()));

		contactUsRepository.save(	new ContactUsEntity(
															contactUsForm.getName(),
															contactUsForm.getSubject(),
															contactUsForm.getMessage(),
															ipAddressRepository.findOneByIpAddress(Statistic.getIpAddress(request)),
															referenceNumberEntity,
															emailEntity,
															ContactUsStatus.TO_ANSWER));

		newMessageInformer.notifyAll();
//		eMailWorker.sendEMail(emaleFrom, "New ContactUs Message", "http://www.fashionprofinder.com/management/messages", null);
	}

	@RequestMapping("/signup/forms")
	public String signUp(ContactUsForm contactUsForm, @Valid AddSiteForm addSiteForm, BindingResult bindingResult, Model model){
		logger.entry(addSiteForm);

		if (bindingResult.hasErrors())
			return returnToForms("Add URL", null, model);

		return returnToForms("Confirmation", MenuSelection.ADD_URL_ADDRESS, model);
	}

	public String returnToForms(String title, MenuSelection formSelection, Model model) {
		logger.entry(title, formSelection);
		if(title!=null)
			model.addAttribute("title", title);
		model.addAttribute("result", true);
		model.addAttribute("forms", true);
		if(formSelection!=null)
			model.addAttribute("menuSelection", formSelection);
		model.addAttribute("letters", homeController.getAvailableLetters());
		return "search";
	}
}
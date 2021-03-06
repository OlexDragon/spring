package jk.web.controllers.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import jk.web.HomeController;
import jk.web.beans.view.SignUpView;
import jk.web.controllers.management.NewMessageInformer;
import jk.web.entities.AddressEntity;
import jk.web.entities.ContactUsEntity.ContactUsStatus;
import jk.web.entities.EmailEntity;
import jk.web.entities.ReferenceNumberEntity;
import jk.web.entities.TelephonEntity;
import jk.web.entities.UrlEntity;
import jk.web.entities.business.BusinessEntity;
import jk.web.entities.business.BusinessTelephonEntity;
import jk.web.entities.business.BusinessUrlEntity;
import jk.web.entities.repositories.BusinessRepository;
import jk.web.entities.repositories.ContactUsRepository;
import jk.web.entities.repositories.ReferenceNumberRepository;
import jk.web.entities.repositories.UserContactEmailRepository;
import jk.web.entities.user.LoginEntity;
import jk.web.entities.user.TitleEntity;
import jk.web.entities.user.UserContactUsEntity;
import jk.web.entities.user.UserEmailEntity;
import jk.web.entities.user.UserHasEmails;
import jk.web.entities.user.UserHasEmailsPK;
import jk.web.entities.user.repositories.LoginRepository;
import jk.web.entities.user.repositories.TitleRepository;
import jk.web.entities.user.repositories.UserEmailEntityRepository;
import jk.web.entities.user.repositories.UserHasEmailsRepository;
import jk.web.filters.Statistic;
import jk.web.repositories.statictic.IpAddressRepository;
import jk.web.repositories.user.ActivityRepository;
import jk.web.user.Business;
import jk.web.user.User;
import jk.web.user.validators.SignUpFormValidator;
import jk.web.view.components.AddSiteForm;
import jk.web.view.components.ContactUsForm;
import jk.web.workers.AddressWorker;
import jk.web.workers.UserWorker;
import jk.web.workers.email.EMailWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

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
//
//	@RequestMapping(value="/signup", method=RequestMethod.POST)
//	public String signup(User user, BindingResult bindingResult, HttpServletRequest request, Model model, Context context) throws AddressException, MessagingException{
//
//		signUpFormValidator.validate(user, bindingResult);
//
//		if(bindingResult.hasErrors()){
//			addUsernamePasswordRange(model);
//			model.addAttribute("message", "home.welcome");
//			return "home";
//		}
//
//		UserEntity newUser;
//		try {
//			newUser = userWorker.createNewUser(user, mainURL, localeResolver.resolveLocale(request), context);
//		} catch (ParseException | NoSuchAlgorithmException e) {
//			logger.catching(e);
//			bindingResult.rejectValue("year", "SignUpFormValidator.this_field_must_be_filled");
//			addUsernamePasswordRange(model);
//			model.addAttribute("message", "home.welcome");
//			return "home";
//		}
//
//		if(newUser.getId()!=null){
//			authenticateUserAndSetSession( user.getUsername(), user.getNewPassword(), request);
//			activityRepository.save(new ActivityEntity().setUserEntity(newUser).setType(ActivityType.NEW_USER).setUserid(newUser.getId()));
//		}
//		logger.trace("\n\t{}", newUser);
//
//		model.addAttribute("title", "SignupController.signup.confirn.title");
//		model.addAttribute("message", "SignupController.signup.confirn.title");
//		model.addAttribute("contents", "SignupController.signup.confirn.sent");
//		return "message";
//	}

	@RequestMapping(value="/signup/business", method=RequestMethod.GET)
	public String signupBusiness(Business business, Model model){
		logger.entry(RequestMethod.GET);
		signupAttributes(model, titleRepository);

		return "signup_business";
	}

//	@RequestMapping(value="/signup/business", method=RequestMethod.POST)
//	public String signupBusiness(Business business, BindingResult bindingResult, HttpServletRequest request, Model model, Context context){
//		logger.entry(business);
//
//		signUpFormValidator.validate(business, bindingResult);
//
//		if(bindingResult.hasErrors()){
//			logger.trace("\n\tbindingResult.hasErrors() == true\n\t{}", bindingResult.getAllErrors());
//			return "signup_business";
//		}
//
//		//save Login Entity
//		LoginEntity le = userWorker.createNewLoginEntity(business.getUsername(), business.getNewPassword(), Permission.BUSINESS);
//
//		if(le.getId()!=null){
//
//			//save e-mail Entity
//			userWorker.saveEMail(business.getEMail());
//
//			//Fill User Entity
//			UserEntity ue = userWorker.getUserEntity();
//			ue.setFirstName(business.getFirstName());
//			ue.setLastName(business.getLastName());
//			userWorker.save();
//
//			//Create Business Entity
//			BusinessEntity be = new BusinessEntity();
//			be.setCompanyName(business.getCompany());
////			be.setCondition(business.getCondition());
//			be.setVatNumber(business.getVatNumber());
//			try {
//				userWorker.saveBusinessEntity(be);
//			} catch (PropertyException e) {
//				logger.catching(e);
//			}
//
//			//create Address Entity
//			AddressEntity ae = new AddressEntity();
//			String a = business.getAddress1();
//			if(business.getAddress2()!=null)
//				a += "\n"+business.getAddress2();
//			ae.setAddress(a);
//			ae.setCity(business.getCity());
//			ae.setCountryCode(business.getCountry());
//			ae.setPostalCode(business.getPostalcode());
//			ae.setStatus(AddressStatus.ACTIVE);
//			userWorker.saveAddress(ae);
//
//			logger.trace("\n\t{}\n\t{}\n\t{}", le, ue, ae);
//		}
//
//		return "message";
//	}

	public void addUsernamePasswordRange(Model model) {
		model.addAttribute("usernameRange", signUpFormValidator.getUsernameRange());
		model.addAttribute("passwordRange", signUpFormValidator.getPasswordRange());
		signupAttributes(model, titleRepository);
	}

//	private void authenticateUserAndSetSession(String username, String password, HttpServletRequest request) {
//		logger.entry(username, password);
//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( username, password);
//
//		// generate session if one doesn't exist
//		request.getSession();
//
//		token.setDetails(new WebAuthenticationDetails(request));
//		Authentication authenticatedUser = authenticationManager.authenticate(token);
//
//		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
//	}

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

//	@RequestMapping(value="/confirm/{userId}/{eMailId}/{passwordHashCode}")
//	public String confirm(@PathVariable Long userId, @PathVariable Long eMailId, @PathVariable Integer passwordHashCode, Model model, Principal principal, HttpServletRequest request){
//		logger.entry(userId, eMailId, passwordHashCode);
//
//		String textCode;
//		User user = new User();
//		if(userId==null || eMailId==null || passwordHashCode==null)
//			logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.error");
//		else{
//			LoginEntity loginEntity = userWorker.getLoginEntity(userId);
//			if(loginEntity==null){
//
//				logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.error");
//				try { request.logout(); } catch (ServletException e) { logger.catching(e); }
//
//			}else if(loginEntity.getPassword().hashCode()==passwordHashCode){
//
//				user.setUsername(loginEntity.getUsername());
//
//				String username = principal.getName();
//				if(username!=null){
//					LoginEntity le = userWorker.getLoginEntity(username);
//					if(le.getId()!=loginEntity.getId())
//						try { request.logout(); } catch (ServletException e) { logger.catching(e); }
//				}
//
//				EMailEntity em = getEmailById(loginEntity, eMailId);
//				if(em!=null){
//					if(em.getStatus()==EMailStatus.TO_CONFIRM){
//						em.setStatus(EMailStatus.ACTIVE);
////						userWorker.saveEMail(em);
//						Long permissions = loginEntity.getPermissions();
//						logger.trace(permissions);
//						if(permissions==null){
//							loginEntity.setPermissions(Permission.DEFAULT.toLong());
//							userWorker.save(loginEntity);
//							logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.confirm");
//						}else{
//							userWorker.save(loginEntity);
//							logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.confirmed");
//						}
//					}else
//						logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.confirmed");
////					authenticateUserAndSetSession( user.getUsername(), user.getNewPassword(), request);
//				}else
//					logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.error");
//			}else
//				logger.error("\n\tpassword hash code({})!=@PathVariable({})\n\t{}",loginEntity.getPassword().hashCode(), passwordHashCode, textCode = "SignupController.confirm.error");
//		}
//		model.addAttribute(user);
//		model.addAttribute("message", textCode);
//		return "home";
//	}

//	private EMailEntity getEmailById(LoginEntity loginEntity, Long eMailId) {
//		List<EMailEntity> emails = loginEntity.getEmails();
//		EMailEntity emailEntity = null;
//		for(EMailEntity ee:emails)
//			if(ee.getId().equals(eMailId)){
//				emailEntity = ee;
//			}
//		return emailEntity;
//	}

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
		ADD_SITE_URL,	
		CONFIRM_MESSAGE,	//confirmation message
		MANAGEMENT_STATISTIC,
		MANAGEMENT_USERS,
		MANAGEMENT_EDIT_CATEGORIES,
		MANAGEMENT_MESSAGES,
		SIGN_UP
	}
	@Autowired
	private HomeController homeController;
	@Autowired
	private IpAddressRepository ipAddressRepository;
	@Autowired
	private UserContactEmailRepository userContactEmailRepository;
	@Autowired
	private ReferenceNumberRepository referenceNumberRepository;
	@Autowired
	private ContactUsRepository contactUsRepository;
	@Autowired
	private NewMessageInformer newMessageInformer;
	//Values from application.properties file
	@Value("${jk.email.from}")
	private String emaleFrom;


	@RequestMapping(value="sign-up", method=RequestMethod.GET)
	public String signUp(SignUpView signUpView, Model model){
		model.addAttribute("userMenu", true);
		return returnToForms("sign_up", MenuSelection.SIGN_UP, model);
	}

	@RequestMapping(value="sign-up", method=RequestMethod.POST)
	public String signUp(SignUpView signUpView, BindingResult bindingResult, Model model){
		if (bindingResult.hasErrors() || !signUp(signUpView, bindingResult)){
			model.addAttribute("userMenu", true);
			return returnToForms("sign_up", MenuSelection.SIGN_UP, model);
		}

		model.addAttribute("messageTitle", "sign_up.confirmation.title");
		model.addAttribute("content", new String[]{"sign_up.confirmation.thank_you", "sign_up.confirmation"});

		return returnToForms("Confirmation", MenuSelection.CONFIRM_MESSAGE, model);
	}

	@Value("${user.username.range.min}")
	private String usernameMinRange;
	@Value("${user.password.range.min}")
	private String passwordMinRange;
	@Value("${user.username.range.max}")
	private String usernameMaxRange;
	@Value("${user.password.range.max}")
	private String passwordMaxRange;
	@Autowired
	private LoginRepository loginRepository;
	private boolean signUp(SignUpView signUpView, BindingResult bindingResult) {

		String username = signUpView.getUsername();
		ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "username", "SignUpFormValidator.this_field_must_be_filled");

		String email = signUpView.getEmail();
		ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "email", "SignUpFormValidator.this_field_must_be_filled");

		String password = signUpView.getPassword();
		ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "password", "SignUpFormValidator.this_field_must_be_filled");

		if(!bindingResult.hasErrors())

			if(username.length()>=Integer.parseInt(usernameMinRange) && username.length()<=Integer.parseInt(usernameMaxRange)){
				LoginEntity loginEntity = loginRepository.findOneByUsername(username);

				if(loginEntity==null){

					loginEntity = loginRepository.findOneByHasEmailsEmailEntityEmail(email);

					if(loginEntity==null) {

						if(password.length()>=Integer.parseInt(passwordMinRange) && password.length()<=Integer.parseInt(passwordMaxRange)){

							if(password.equals(signUpView.getConfirmPassword())){
								signUp(signUpView);
								return true;
							}else
								bindingResult.rejectValue("confirmPassword", "SignUpFormValidator.Not match", "Not Match");

						}else
							bindingResult.rejectValue("password", "SignUpFormValidator.between_min_and_max_characters", new String[]{passwordMinRange, passwordMaxRange}, "Between {0} and {1} characters.");
					}else
						bindingResult.rejectValue("email", "SignUpFormValidator.this_email_already_exists", "Exists");
				}else
					bindingResult.rejectValue("username", "SignUpFormValidator.user_name_already_exists", new String[]{username}, "This username already exists.");
			}else
				bindingResult.rejectValue("username", "SignUpFormValidator.between_min_and_max_characters", new String[]{usernameMinRange, usernameMaxRange}, "Between {0} and {1} characters.");

		return false;
	}

	@Autowired
	private UserHasEmailsRepository userHasEmailsRepository;
	@Autowired
	private UserEmailEntityRepository userEmailEntityRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	private void signUp(SignUpView signUpView) {

		//Email
		String email = signUpView.getEmail();
		UserEmailEntity userEmailEntity = userEmailEntityRepository.findOneByEmail(email);
		if(userEmailEntity==null)
			userEmailEntity = userEmailEntityRepository.save(new UserEmailEntity(email));
		List<UserEmailEntity> emailEntities = new ArrayList<>();
		emailEntities.add(userEmailEntity);

		//Login
		LoginEntity loginEntity = loginRepository.save(new LoginEntity(signUpView.getUsername(), passwordEncoder.encode(signUpView.getPassword())));
		List<LoginEntity> loginEntityList = new ArrayList<>();
		loginEntityList.add(loginEntity);

		//Login and Email
		UserHasEmails hasEmails = new UserHasEmails(new UserHasEmailsPK(loginEntity.getId(), userEmailEntity.getEmailId()));

		List<UserHasEmails> userHasEmails = new ArrayList<>();
		userHasEmails.add(hasEmails);

		userHasEmailsRepository.save(hasEmails);
	}

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
		UserEmailEntity emailEntity = userContactEmailRepository.findOneByEmail(contactUsForm.getEmail());
		if(emailEntity==null)
			emailEntity = userContactEmailRepository.save(new UserEmailEntity(contactUsForm.getEmail()));

		ReferenceNumberEntity referenceNumberEntity = referenceNumberRepository.findOneByReferenceNumber(contactUsForm.getReferenceNumber());
		if(referenceNumberEntity==null)
			referenceNumberEntity = referenceNumberRepository.save(new ReferenceNumberEntity(contactUsForm.getReferenceNumber()));

		contactUsRepository.save(	new UserContactUsEntity(
															contactUsForm.getName(),
															contactUsForm.getSubject(),
															contactUsForm.getMessage(),
															ipAddressRepository.findOneByIpAddress(Statistic.getIpAddress(request)),
															referenceNumberEntity,
															emailEntity,
															ContactUsStatus.TO_ANSWER));

		newMessageInformer.checkFotNewMessages();
//		eMailWorker.sendEMail(emaleFrom, "New ContactUs Message", "http://www.fashionprofinder.com/management/messages", null);
	}

	@Autowired
	private AddressWorker addressWorker;

	//User submenu
	@RequestMapping("add-site")
	public String addSite(@Valid AddSiteForm addSiteForm, BindingResult bindingResult, Model model){
		logger.entry(addSiteForm);

		if (bindingResult.hasErrors() || addSiteForm.getSiteAddress()==null || !addSite(addSiteForm, model)){
			model.addAttribute("userMenu", true);
			return returnToForms("Add URL", MenuSelection.ADD_SITE_URL, model);
		}

		model.addAttribute("messageTitle", "add_site_url_confirmation");
		model.addAttribute("content", new String[]{"thank_you", "all_the_best"});

		return returnToForms("Confirmation", MenuSelection.CONFIRM_MESSAGE, model);
	}

	@Autowired
	private BusinessRepository businessRepository;
	private boolean addSite(AddSiteForm addSiteForm, Model model) {
		boolean hasAdded = false;

		BusinessEntity be = getBusinessEntity(addSiteForm);

		addAdderssEntity	(addSiteForm, be);
		addEmailEntity		(addSiteForm, be);
		addTelephonEntity	(addSiteForm, be);
		addUrlEntity		(addSiteForm, be);

		businessRepository.save(be);

		return hasAdded;
	}

	private boolean addUrlEntity(AddSiteForm addSiteForm, BusinessEntity be) {
		List<BusinessUrlEntity> uel = be.getUrlEntityList();

		if(uel==null)
			be.setUrlEntityList(uel = new ArrayList<>());

		String url = addSiteForm.getSiteAddress();

		boolean createNew = true;
		for(UrlEntity cee:uel){
			if(cee.getUrl().equalsIgnoreCase(url)){
				createNew = false;
				break;
			}
		}

		if(createNew)
			uel.add(new BusinessUrlEntity(url));

		return createNew;
	}

	private boolean addTelephonEntity(AddSiteForm addSiteForm, BusinessEntity be) {
		List<BusinessTelephonEntity> tel = be.getTelephonEntityList();

		if(tel==null)
			be.setTelephonEntityList(tel = new ArrayList<>());

		String phon = addSiteForm.getPhone().replaceAll("\\D", "");

		boolean createNew = true;
		for(TelephonEntity te:tel){
			if(te.getTelephon().equalsIgnoreCase(phon)){
				createNew = false;
				break;
			}
		}

		if(createNew)
			tel.add(new BusinessTelephonEntity(phon));

		return createNew;
	}

	private boolean addEmailEntity(AddSiteForm addSiteForm, BusinessEntity be) {
		List<EmailEntity> ceel = be.getContactEmailEntityList();

		if(ceel==null)
			be.setContactEmailEntityList(ceel = new ArrayList<>());

		String email = addSiteForm.getEmail();

		boolean createNew = true;
		for(EmailEntity cee:ceel){
			if(cee.getEmail().equalsIgnoreCase(email)){
				createNew = false;
				break;
			}
		}

		if(createNew)
			ceel.add(new EmailEntity(email));

		return createNew;
	}

	private boolean addAdderssEntity(AddSiteForm addSiteForm, BusinessEntity be) {
		List<AddressEntity> ael = be.getAddressEntityList();

		if(ael==null)
			be.setAddressEntityList(ael = new ArrayList<>());

		String address = addSiteForm.getAddress();
		String city = addSiteForm.getCity();
		String region = addSiteForm.getProvinceState();
		String countryCode = addSiteForm.getCountry();
		String postalCode = addSiteForm.getPostalCode();

		boolean createNew = true;
		for(AddressEntity ae:ael){
			if(ae.getAddress().equalsIgnoreCase(address) &&
					ae.getCity().equalsIgnoreCase(city) &&
					ae.getCountryCode().equalsIgnoreCase(countryCode) &&
					ae.getPostalCode().equalsIgnoreCase(postalCode) &&
					ae.getRegion().equalsIgnoreCase(region)){
				createNew = false;
				break;
			}			
		}

		if(createNew)
			ael.add(new AddressEntity(address, city, region, countryCode, postalCode));

		return createNew;
	}

	private BusinessEntity getBusinessEntity(AddSiteForm addSiteForm) {
		BusinessEntity be = businessRepository.findByVatNumberAndCompanyName(addSiteForm.getVatNumber(), addSiteForm.getCompanyName());
		if(be==null){
			be = new BusinessEntity();
			be.setCompanyName(addSiteForm.getCompanyName());
			be.setVatNumber(addSiteForm.getVatNumber());
		}
		return be;
	}

	public String returnToForms(String title, MenuSelection formSelection, Model model) {
		logger.entry(title, formSelection);
		if(title!=null)
			model.addAttribute("title", title);
		model.addAttribute("result", true);
		model.addAttribute("forms", true);
		if(formSelection!=null)
			model.addAttribute("menuSelection", formSelection);
		return "search";
	}
}
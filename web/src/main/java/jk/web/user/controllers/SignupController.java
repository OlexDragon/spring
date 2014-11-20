package jk.web.user.controllers;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import jk.web.user.User;
import jk.web.user.entities.ActivityEntity;
import jk.web.user.entities.EMailEntity;
import jk.web.user.entities.EMailEntity.EMailStatus;
import jk.web.user.entities.LoginEntity;
import jk.web.user.entities.UserEntity;
import jk.web.user.entities.UserEntity.ActivityType;
import jk.web.user.repository.ActivityRepository;
import jk.web.user.repository.LoginRepository.Permission;
import jk.web.user.repository.TitleRepository;
import jk.web.user.validators.SignUpFormValidator;
import jk.web.workers.EMailWorker;
import jk.web.workers.UserWorker;

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
import org.springframework.web.servlet.LocaleResolver;

@Controller
public class SignupController {

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
		model.addAttribute("text", "SignupController.signup.error");
		return "home";
	}

	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(User user, BindingResult bindingResult, HttpServletRequest request, Model model) throws AddressException, MessagingException{

		signUpFormValidator.validate(user, bindingResult);

		if(bindingResult.hasErrors()){
			model.addAttribute("usernameRange", signUpFormValidator.getUsernameRange());
			model.addAttribute("passwordRange", signUpFormValidator.getPasswordRange());
			model.addAttribute("text", "SignupController.signup.error");
			return "home";
		}

		UserEntity newUser;
		try {
			newUser = userWorker.createNewUser(user, mainURL, localeResolver.resolveLocale(request));
		} catch (ParseException | NoSuchAlgorithmException e) {
			logger.catching(e);
			bindingResult.rejectValue("year", "SignUpFormValidator.this_field_must_be_filled");
			model.addAttribute("usernameRange", signUpFormValidator.getUsernameRange());
			model.addAttribute("passwordRange", signUpFormValidator.getPasswordRange());
			model.addAttribute("text", "SignupController.signup.error");
			return "home";
		}

		if(newUser.getId()!=null){
			authenticateUserAndSetSession(user, request);
			activityRepository.save(new ActivityEntity().setUserEntity(newUser).setType(ActivityType.NEW_USER).setUserid(newUser.getId()));
		}
		logger.trace("\n\t{}", newUser);

		model.addAttribute(yearsList);
		model.addAttribute("text", "SignupController.signup.confirn.sent");
		return "home";
	}

	private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
		logger.entry(user);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( user.getUsername(), user.getNewPassword());

		// generate session if one doesn't exist
		request.getSession();

		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}

	@RequestMapping(value="/signup/clear")
	public String clear(User signUpForm){
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
	public String confirm(@PathVariable Long userId, @PathVariable Long eMailId, @PathVariable Integer passwordHashCode, Model model){
		logger.entry(userId, eMailId, passwordHashCode);

		String textCode;
		User user = new User();
		if(userId==null || eMailId==null || passwordHashCode==null)
			logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.error");
		else{
			LoginEntity loginEntity = userWorker.getLoginEntity(userId);
			if(loginEntity==null)
				logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.error");
			else if(loginEntity.getPassword().hashCode()==passwordHashCode){
				user.setUsername(loginEntity.getUsername());

				EMailEntity em = getEmailById(loginEntity, eMailId);
				if(em!=null){
					if(em.getStatus()==EMailStatus.TO_CONFIRM){
						em.setStatus(EMailStatus.ACTIVE);
//						userWorker.saveEMail(em);
						Permission permissions = loginEntity.getPermissions();
						logger.trace(permissions);
						if(permissions==null){
							loginEntity.setPermissions(Permission.DEFAULT);
							userWorker.save(loginEntity);
							logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.confirm");
						}else{
							userWorker.save(loginEntity);
							logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.confirmed");
						}
					}else
						logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.confirmed");
				}else
					logger.trace("\n\ttextCode = {}", textCode = "SignupController.confirm.error");
			}else
				logger.error("\n\tpassword hash code({})!=@PathVariable({})\n\t{}",loginEntity.getPassword().hashCode(), passwordHashCode, textCode = "SignupController.confirm.error");
		}
		model.addAttribute(user);
		model.addAttribute("text", textCode);
		SignupController.signupAttributes(model, titleRepository);
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

		if(	SecurityContextHolder.getContext().getAuthentication() == null ||
				!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){

			model.addAttribute("tiles", titleRepository.findAll(new Sort("id")));
			model.addAttribute("yearsList", getYearsList());
		}
	}
}
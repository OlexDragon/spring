package jk.web.user.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import jk.web.configuration.EMailConfig.ConfirmStatus;
import jk.web.user.User;
import jk.web.user.entities.EMailEntity;
import jk.web.user.entities.EMailEntity.EMailStatus;
import jk.web.user.entities.LoginEntity;
import jk.web.user.entities.UserEntity;
import jk.web.user.repository.LoginRepository.Permission;
import jk.web.user.validators.SignUpFormValidator;
import jk.web.workers.EMailWorker;
import jk.web.workers.UserWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


	@Value("${main.url}")
	private String mainURL;

	private static int year;
	private static List<String> yearsList;

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signup(User user, Model model){
		logger.entry(RequestMethod.GET);
		model.addAttribute("yearsList", getYearsList());
		return "home";
	}

	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(User user, BindingResult bindingResult, HttpServletRequest request, Model model) throws AddressException, MessagingException{

		signUpFormValidator.validate(user, bindingResult);
		model.addAttribute("yearsList", getYearsList());

		if(bindingResult.hasErrors()){
			model.addAttribute("usernameRange", signUpFormValidator.getUsernameRange());
			model.addAttribute("passwordRange", signUpFormValidator.getPasswordRange());
			return "home";
		}

		UserEntity newUser;
		try {
			newUser = userWorker.createNewUser(user);
		} catch (ParseException e) {
			logger.catching(e);
			bindingResult.rejectValue("year", "SignUpFormValidator.this_field_must_be_filled");
			model.addAttribute("usernameRange", signUpFormValidator.getUsernameRange());
			model.addAttribute("passwordRange", signUpFormValidator.getPasswordRange());
			return "home";
		}

		if(newUser.getId()!=null){
			eMailWorker.sendRegistrationMail(user, mainURL+"/confirm", localeResolver.resolveLocale(request));
			authenticateUserAndSetSession(user, request);

		}
		logger.trace("\n\t{}", newUser);

		model.addAttribute(yearsList);
		model.addAttribute("status", ConfirmStatus.SENT);
		return "confirm";
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

	@RequestMapping(value="/confirm/{username}")
	public String confirm(@PathVariable String username, @RequestParam(required = false) String eMail, Model model){
		logger.entry(username, eMail);

		if(username==null || eMail==null)
			return"login";

		eMail = eMail.toLowerCase();
		UserEntity userEntity = userWorker.getUserEntity(username);
		if(userEntity==null)
			model.addAttribute("status", ConfirmStatus.ERROR);
		else{
			LoginEntity loginEntity = userEntity.getLoginEntity();
			if(loginEntity==null)
				model.addAttribute("status", ConfirmStatus.ERROR);
			else{
				EMailEntity em = userWorker.getEmailToConfirm();
				if(em!=null && em.getEMail().equals(eMail)){
					if(em.getStatus()==EMailStatus.TO_CONFIRM){
						em.setStatus(EMailStatus.ACTIVE);
						userWorker.saveEMail(em);
						Permission permissions = loginEntity.getPermissions();
						logger.trace(permissions);
						if(permissions==null){
							loginEntity.setPermissions(Permission.DEFAULT);
							userWorker.save(loginEntity);
							model.addAttribute("uname", username);
							model.addAttribute("status", ConfirmStatus.CONFIRMED);
							return logger.exit("login");
						}else
							return logger.exit("login");
					}else
						return logger.exit("login");
				}else
					model.addAttribute("status", ConfirmStatus.ERROR);
			}
		}
		model.addAttribute("uname", username);
		return logger.exit("confirm");
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
}
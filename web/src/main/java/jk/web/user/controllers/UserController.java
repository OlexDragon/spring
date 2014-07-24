package jk.web.user.controllers;

import java.security.Principal;
import java.sql.Timestamp;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import jk.web.configuration.EMailConfig.ConfirmStatus;
import jk.web.user.SignUpForm;
import jk.web.user.entities.EMailEntity;
import jk.web.user.entities.EMailEntity.EMailStatus;
import jk.web.user.entities.LoginEntity;
import jk.web.user.entities.UserEntity;
import jk.web.user.repository.EMailRepository;
import jk.web.user.repository.UserRepository;
import jk.web.user.repository.LoginRepository.Permission;
import jk.web.user.validators.SignUpFormValidator;
import jk.web.workers.EMailWorker;
import jk.web.workers.LoginWorker;
import jk.web.workers.UserWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

@Controller
public class UserController {

	private final Logger logger = LogManager.getLogger();

	@Value("${confirm.url}")
	private String confirmURL;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EMailWorker eMailWorker;
	@Autowired
	private LocaleResolver localeResolver;
	@Autowired
	private LoginWorker loginWorker;
	@Autowired
	private UserWorker userWorker;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EMailRepository eMailRepository;

	@RequestMapping(value={"/login", "/logout"})
	public String login(){
		logger.entry();
		return "login";
	}

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signup(SignUpForm signUpBean){
		logger.entry(RequestMethod.GET);
		return "signup";
	}

	@RequestMapping(value="/user", method=RequestMethod.GET)
	public String user(Principal principal, Model model){
		String username = principal.getName();
		logger.entry(username);
		if(username==null){
			return "login";
		}

		UserEntity userEntity = userWorker.getUserEntity(username);
		if(userEntity==null)
			return "login";

		model.addAttribute("user", userEntity);
		return "user";
	}

	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(SignUpForm signUpForm, BindingResult bindingResult, HttpServletRequest request, Model model) throws AddressException, MessagingException{

		SignUpFormValidator validator = new SignUpFormValidator(userRepository);
		validator.validate(signUpForm, bindingResult);

		boolean hasErrors = bindingResult.hasErrors();
		logger.trace("{}, hasErrors={}", RequestMethod.POST, hasErrors);

		if(hasErrors)
			return "signup";

		UserEntity newUser = createNewUser(signUpForm);

		if(newUser.getId()!=null)
			eMailWorker.sendRegistrationMail(signUpForm, confirmURL, localeResolver.resolveLocale(request));
		logger.trace("\n\t{}", newUser);

		model.addAttribute("status", ConfirmStatus.SENT);
		return "confirm";
	}

	@RequestMapping(value="/signup/clear")
	public String clear(SignUpForm signUpForm){
		signUpForm.setUsername(null);
		signUpForm.setFirstName(null);
		signUpForm.setLastName(null);
		signUpForm.setSex(null);
		signUpForm.setBirthday(null);
		signUpForm.setEMail(null);
		return "signup";
	}

        @RequestMapping(value="/confirm/{username}")
	public String confirm(@PathVariable String username, @RequestParam(required = false) String email, Model model){
		logger.entry(username, email);

		if(username==null || email==null)
			return"login";

		UserEntity userEntity = userWorker.getUserEntity(username);
		if(userEntity==null)
			model.addAttribute("status", ConfirmStatus.ERROR);
		else{
			LoginEntity loginEntity = userEntity.getLoginEntity();
			if(loginEntity==null)
				model.addAttribute("status", ConfirmStatus.ERROR);
			else{
				EMailEntity em = userWorker.getEmailToConfirm();
				if(em!=null && em.getEMail().equals(email)){
					if(em.getStatus()==EMailStatus.TO_CONFIRM){
						em.setStatus(EMailStatus.CONFIRMED);
						Permission permissions = loginEntity.getPermissions();
						if(permissions==null){
							loginEntity.setPermissions(Permission.DEFAULT);
							model.addAttribute("status", ConfirmStatus.CONFIRMED);
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

	private UserEntity createNewUser(SignUpForm signUpForm) throws AddressException, MessagingException {

		LoginEntity loginEntity = loginWorker.save( new LoginEntity(signUpForm.getUsername(), passwordEncoder.encode(signUpForm.getPassword())));

		userWorker.setUserEntity(new UserEntity(loginEntity.getId(), signUpForm.getFirstName(), signUpForm.getLastName(), new Timestamp(signUpForm.getBirthday().getTime()), signUpForm.getSex()));
		userWorker.addEMail(eMailRepository.save(new EMailEntity().setId(loginEntity.getId()).setEMail(signUpForm.getEMail())));
		logger.trace("\n\t{}\n\t{}", signUpForm, loginEntity);
		return userWorker.save();
	}
}

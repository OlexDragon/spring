package jk.web.user.controllers;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jk.web.user.User;
import jk.web.user.entities.LoginEntity;
import jk.web.user.entities.UserEntity;
import jk.web.user.repository.LoginRepository;
import jk.web.user.repository.TitleRepository;
import jk.web.user.validators.SignUpFormValidator;
import jk.web.workers.EMailWorker;
import jk.web.workers.UserWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private SignUpFormValidator signUpFormValidator;
	@Autowired
	private UserWorker userWorker;
	@Autowired
	private EMailWorker eMailWorker;
	@Autowired
	protected ApplicationContext applicationContext;
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private TitleRepository titleRepository;

	@Value("${main.url}")
	private String mainURL;

	@RequestMapping
	public String login(User user) {
		logger.entry(user);
		user.setTitles(titleRepository.findAll(new Sort("id")));
		return "home";
	}

	@RequestMapping(value="forgot", method=RequestMethod.GET)
	public String forgotPassword(){
		logger.entry();
		return "forgot_password";
	}

	@RequestMapping(value="forgot", method=RequestMethod.POST)
	public String resetPassword(@Param(value = "eMail") String eMail, Model model) throws NoSuchMessageException, UnsupportedEncodingException{
		logger.entry(eMail);
		eMail = eMail.toLowerCase();
		if(eMail.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")){
			UserEntity userEntity = userWorker.getUserEntityByEMail(eMail);
			Locale locale = userWorker.getLocale();
			if(userEntity!=null) {
				eMailWorker.sendEMail(
									eMail,
									applicationContext.getMessage(
																"LoginController.forgot_password_title",
																null,
																locale),

									applicationContext.getMessage(
																"LoginController.forgot_password",
																new String[]{
																		mainURL,
																		""+userEntity.getId(),
																		 userEntity.getLoginEntity().getPassword()
																},
																locale));
				model.addAttribute("message", "LoginController.forgot_password_title_message");
			}else
				model.addAttribute("errorMessage", "LoginController.email_not_exists");
		}else{
			model.addAttribute("eMail", eMail);
			model.addAttribute("errorMessage", "SignUpFormValidator.please_write_a_valid_email_address");
		}

		return "forgot_password";
	}

	@RequestMapping(value="new_password/{id}", method=RequestMethod.GET)
	public String resetPassword(@PathVariable long id, @RequestParam(value="p", required=false) String password, Model model) throws UnsupportedEncodingException{
		if(password==null){
			pageRefreshed(model);
			return "message";
		}
		logger.entry(id, password);
		LoginEntity loginEntity = loginRepository.findOne(id);
		if(loginEntity!=null){
//			String encode = URLEncoder.encode(loginEntity.getPassword(), "UTF-8");
			if(loginEntity.getPassword().equals(password)){
				userWorker.setUser(loginEntity.getUsername());
			}else{
				model.addAttribute("title", "LoginController.expired_link_title");
				model.addAttribute("message", "LoginController.expired_link");
				return "message";
			}

		}
		return "resetPassword";
	}

	@RequestMapping(value="new_password", method=RequestMethod.GET)
	public String pageRefresh(Model model){
		pageRefreshed(model);
		return "message";
	}

	private void pageRefreshed(Model model) {
		model.addAttribute("title", "LoginController.page_refreshed_title");
		model.addAttribute("message", "LoginController.page_refreshed");
	}

	@RequestMapping(value="new_password", method=RequestMethod.POST)
	public String submitPassword(@Param(value="newPassword") String newPassword, @Param(value="repassword") String repassword, Model model){
		logger.entry(newPassword, repassword);

		Integer min = signUpFormValidator.getPasswordRange().get("min");
		Locale locale = userWorker.getLocale();
		if(newPassword.length()<min)
			model.addAttribute(
					"errorPassword",
					applicationContext.getMessage(
							"SignUpFormValidator.password_min",
							new Integer[]{ min },
							locale));
		else {
			Integer max = signUpFormValidator.getPasswordRange().get("max");
			if(newPassword.length()>max)
				model.addAttribute(
						"errorPassword",
						applicationContext.getMessage(
								"SignUpFormValidator.between_min_and_max_characters",
								new Integer[]{ min, max },
								locale));
			else if(newPassword.equals(repassword)){
				userWorker.saveNewPassword(newPassword);
				return "login";
			}else{
				model.addAttribute(
						"errorPassword",
						applicationContext.getMessage(
								"SignUpFormValidator.these_passwords_dont_match",
								null,
								locale));
				model.addAttribute(
						"errorRepassword",
						applicationContext.getMessage(
								"SignUpFormValidator.these_passwords_dont_match",
								null,
								locale));
			}
		}
		
		model.addAttribute("oldPassword", newPassword);
		model.addAttribute("oldPassword", repassword);
		return "resetPassword";
	}

	public static void logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) 
		    session.invalidate();
		SecurityContextHolder.clearContext();
	}
}

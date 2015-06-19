package jk.web.controllers.user;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import jk.web.entities.user.LoginEntity;
import jk.web.entities.user.repositories.LoginRepository;
import jk.web.repositories.user.TitleRepository;
import jk.web.user.LoginView;
import jk.web.user.validators.SignUpFormValidator;
import jk.web.workers.UserWorker;
import jk.web.workers.email.EMailWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.query.Param;
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
	public String login(LoginView loginView, Model model) {
		logger.entry(loginView);
		model.addAttribute("login", true);
		return "search";
	}

	@RequestMapping(value="forgot", method=RequestMethod.GET)
	public String forgotPassword(){
		logger.entry();
		return "forgot_password";
	}

//	@RequestMapping(value="forgot", method=RequestMethod.POST)
//	public String resetPassword(@Param(value = "eMail") String eMail, Model model) throws NoSuchMessageException, UnsupportedEncodingException{
//		logger.entry(eMail);
//		eMail = eMail.toLowerCase();
//		Locale locale = userWorker.getLocale();
//		if(eMail.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")){
//			UserEntity userEntity = userWorker.getUserEntityByEMail(eMail);
//			if(userEntity!=null) {
//				eMailWorker.sendEMail(
//									eMail,
//									applicationContext.getMessage(
//																"LoginController.forgot_password_title",
//																null,
//																locale),
//
//									applicationContext.getMessage(
//																"LoginController.forgot_password",
//																new String[]{
//																		mainURL,
//																		""+userEntity.getId(),
//																		 userEntity.getLoginEntity().getPassword()
//																},
//																locale), null);
//				model.addAttribute("message", "LoginController.forgot_password_title_message");
//			}else
//				model.addAttribute("errorMessage", "LoginController.email_not_exists");
//		}else{
//			model.addAttribute("eMail", eMail);
//			model.addAttribute(
//					"errorMessage",
//					applicationContext.getMessage(
//												"SignUpFormValidator.please_write_a_valid_X",
//												new String[]{"email address"},
//												"Not valid",
//												locale
//					)
//			);
//		}
//
//		return "forgot_password";
//	}

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
//				userWorker.saveNewPassword(newPassword);
				return "login";
			}else{
				model.addAttribute(
						"errorPassword",
						applicationContext.getMessage(
								"SignUpFormValidator.these_X_dont_match",
								new String[]{"passwords"},
								locale));
				model.addAttribute(
						"errorRepassword",
						applicationContext.getMessage(
								"SignUpFormValidator.these_X_dont_match",
								new String[]{"passwords"},
								locale));
			}
		}

		//TODO
		model.addAttribute("newPassword", newPassword);
		model.addAttribute("repassword", repassword);
		return "resetPassword";
	}

	@RequestMapping("inputs")
	public String getLogInInputs(Model model){
		model.addAttribute("loginView", new LoginView());
		return "search :: logInInputs";
	}
}

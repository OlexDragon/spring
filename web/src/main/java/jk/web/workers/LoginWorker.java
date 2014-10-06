package jk.web.workers;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import jk.web.user.entities.LoginEntity;
import jk.web.user.repository.LoginRepository;

public class LoginWorker {

	protected final Logger logger = LogManager.getLogger(getClass().getName());

	@Autowired
	protected ApplicationContext applicationContext;
	@Autowired
	protected EMailWorker eMailWorker;
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	protected PasswordEncoder passwordEncoder;

	protected Locale locale;

	public LoginEntity getLoginEntity(String username) {
		return loginRepository.findByUsername(username);
	}

	public LoginEntity save(LoginEntity loginEntity) {
		return loginRepository.save(loginEntity);
	}

	public boolean existsUserName(String username) {
		return loginRepository.exists(username);
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public LoginEntity saveNewUsername(LoginEntity loginEntity, String eMail) {
		save(loginEntity);
		eMailWorker.sendEMail(	eMail,
				applicationContext.getMessage(	"LoginWorker.Username_had_been_changed",
												null,
												"Username had been changed",
												locale),
				applicationContext.getMessage(	"LoginWorker.Username_had_been_changed_message",
												new String[]{loginEntity.getUsername()},
												"Username had been changed",
												locale));
		return loginEntity;
	}

	public LoginEntity saveNewPassword(LoginEntity loginEntity, String newPassword, String eMail) {
		save(loginEntity.setPassword(passwordEncoder.encode(newPassword)));
		eMailWorker.sendEMail(
				eMail,
				applicationContext.getMessage(	"LoginWorker.Password_had_been_changed",
												null,
												"Username had been changed",
												locale),
				applicationContext.getMessage(	"LoginWorker.Password_had_been_changed_message",
												new String[]{newPassword},
												"Password had been changed",
												locale));
		return loginEntity;
	}
}

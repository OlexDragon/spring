package jk.web.workers;

import java.util.Locale;

import jk.web.entities.user.LoginEntity;
import jk.web.entities.user.LoginEntity.Permission;
import jk.web.repositories.user.LoginRepository;
import jk.web.workers.UserWorker.ConfirmationStaus;
import jk.web.workers.email.EMailWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginWorker {

	protected final Logger logger = LogManager.getLogger(getClass().getName());

	@Autowired
	protected ApplicationContext applicationContext;
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	protected PasswordEncoder passwordEncoder;
	@Autowired
	protected EMailWorker eMailWorker;
//	@Autowired
//	private EMailRepository eMailRepository;

	protected Locale locale;
	protected ConfirmationStaus eMailStatus;

	public LoginEntity createNewLoginEntity(String username, String password, Permission permission) {
		LoginEntity le = new LoginEntity();
		le.setPassword(passwordEncoder.encode(password));
		le.setUsername(username);
		le.setPermissions(permission.toLong());
		return save(le);
	}

	public LoginEntity getLoginEntity(String username) {
		return loginRepository.findByUsername(username);
	}

	public LoginEntity getLoginEntity(Long userId) {
		return loginRepository.findById(userId);
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
												locale), null);
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
												locale), null);
		return loginEntity;
	}

//	public boolean existsEMail(String eMail) {
//		return logger.exit(eMailRepository.exists(eMail));
//	}
//
//	public EMailEntity getEMail(String eMail) {
//		return eMailRepository.findByEMail(eMail);
//	}
//
//	public EMailEntity saveEMail(EMailEntity eMailEntity) {
//		return eMailRepository.save(eMailEntity);
//	}

//	public LoginEntity saveEMail(String username, String eMail) {
//		logger.entry(username, eMail);
//
//		LoginEntity loginEntity = loginRepository.findByUsername(username);
//		if(setEmail(loginEntity, eMail)){
//			loginEntity = save(loginEntity);
//			sendEMail(loginEntity);
//		}
//
//		return loginEntity;
//	}
//
//	public void sendEMail(LoginEntity loginEntity) {
//		if(eMailStatus!=null){
//			String em = getEMail(loginEntity);
//			switch(eMailStatus){
//			case CONFIRMED_EMAIL:
//				eMailWorker.sendEMail(	em,
//						applicationContext.getMessage(	"UserController.email_had_been_changed",
//														null,
//														"email had been changed",
//														locale),
//						applicationContext.getMessage(	"UserController.email_had_been_changed_message",
//														new String[]{em},
//														"email had been changed",
//														locale), null);
//				break;
//			case NEW_EMAIL:
//				eMailWorker.sendEMail(	em,
//						applicationContext.getMessage(	"UserController.email_confirmation",
//														null,
//														"email confirmation",
//														locale),
//						applicationContext.getMessage(	"UserController.email_confirmation_message",
//														new String[]{em},
//														"email confirmation",
//														locale), null);
//				break;
//			case NEW_USER:
//			}
//			eMailStatus = null;
//		}
//	}

//	private String getEMail(LoginEntity loginEntity) {
//		String eMail = "";
//		if(loginEntity!=null){
//			List<EMailEntity> emails = loginEntity.getEmails();
//			logger.trace("\n\t{}", emails);
//			if(emails!=null)
//				for(EMailEntity eme:emails)
//					if(eme.getStatus()==null || eme.getStatus()==EMailStatus.ACTIVE || eme.getStatus()==EMailStatus.TO_CONFIRM){
//						eMail = eme.getEMail();
//						break;
//					}
//		}
//		return eMail;
//	}
//
//	public boolean setEmail(LoginEntity loginEntity, String eMail) {
//
//		boolean added;
//		if(added = addEMail(loginEntity, eMail)){
//			List<EMailEntity> emails = loginEntity.getEmails();
//			logger.trace("\n\t{}\n\t{}\n\t{}", loginEntity, eMail, emails);
//
//			Date time = Calendar.getInstance().getTime();
//			for(EMailEntity eme:loginEntity.getEmails()){
//				boolean equals = eme.getEMail().equals(eMail);
//				switch(eme.getStatus()){
//				case ACTIVE:
//					if(equals)
//						eMailStatus = ConfirmationStaus.CONFIRMED_EMAIL;
//					else
//						eme.setStatus(EMailStatus.NOT_ACTIVE).setUpdateDate(time);
//					break;
//				case TO_CONFIRM:
//					if(eMailStatus!=ConfirmationStaus.NEW_USER)
//						if(equals)
//							eMailStatus = ConfirmationStaus.NEW_EMAIL;
//						else
//							eme.setStatus(EMailStatus.NOT_CONFIRMED).setUpdateDate(time);
//					break;
//				case NOT_ACTIVE:
//					if(equals){
//						eme.setStatus(EMailStatus.ACTIVE).setUpdateDate(time);
//						eMailStatus = ConfirmationStaus.CONFIRMED_EMAIL;
//					}
//					break;
//				case NOT_CONFIRMED:
//					if(equals){
//						eme.setStatus(EMailStatus.TO_CONFIRM).setUpdateDate(time);
//						eMailStatus = ConfirmationStaus.NEW_EMAIL;
//					}
//					break;
//				}
//			}
//		}
//
//		return added;
//	}
//
//	public boolean addEMail(LoginEntity loginEntity, String eMail) {
//		logger.entry(loginEntity, eMail);
//
//		boolean added;
//		EMailEntity eMailEntity = new EMailEntity().setUserId(loginEntity.getId()).setEMail(eMail);
//
//		List<EMailEntity> emails = loginEntity.getEmails();
//		if(emails==null){
//			emails = new ArrayList<>();
//			loginEntity.setEmails(emails);
//			added = emails.add(eMailEntity);
//		}else if(emails.isEmpty() || !emails.contains(new EMailEntity().setEMail(eMail))){
//			logger.trace("\n\tAdd e-mail: {}", eMail);
//			added = emails.add(eMailEntity);
//		}else
//			added = false;
//
//		return logger.exit(added);
//	}
}

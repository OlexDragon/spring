package jk.web.workers;

import java.util.ArrayList;
import java.util.List;

import jk.web.user.entities.EMailEntity;
import jk.web.user.entities.EMailEntity.EMailStatus;
import jk.web.user.entities.LoginEntity;
import jk.web.user.entities.UserEntity;
import jk.web.user.repository.UserRepository;

public class UserWorker {

	private UserRepository userRepository;
	private LoginWorker loginWorker;

	private UserEntity userEntity;

	public UserWorker(UserRepository userRepository, LoginWorker loginWorker) {
		this.userRepository = userRepository;
		this.loginWorker = loginWorker;
	}

	public UserEntity getUserEntity(String username){
		userEntity = userRepository.findByUsername(username);
		if(userEntity==null){
			LoginEntity loginEntity = loginWorker.getLoginEntity(username);
			if(loginEntity!=null){
				userEntity = new UserEntity(loginEntity.getId());
				userEntity.setLoginEntity(loginEntity);
			}
		}
		return userEntity;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public String toString() {
		return "UserWorker [userEntity=" + userEntity + "]";
	}

	public EMailEntity getEmailToConfirm() {
		EMailEntity eMailEntity = null;
		if(userEntity!=null)
			if(userEntity.getEmails()!=null)
				for(EMailEntity em:userEntity.getEmails())
					if(em.getStatus()==EMailStatus.TO_CONFIRM){
						eMailEntity = em;
						break;
					}
		return eMailEntity;
	}

	public UserEntity save() {
		return this.userEntity = userRepository.save(userEntity);
	}

	public void addEMail(EMailEntity eMail) {
		List<EMailEntity> emails = userEntity.getEmails();
		if(emails==null){
			emails = new ArrayList<>();
			userEntity.setEmail(emails);
		}
		if(!emails.contains(eMail))
			emails.add(eMail);
	}
}

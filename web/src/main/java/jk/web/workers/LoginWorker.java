package jk.web.workers;

import org.springframework.beans.factory.annotation.Autowired;

import jk.web.user.entities.LoginEntity;
import jk.web.user.repository.LoginRepository;

public class LoginWorker {

	@Autowired
	private LoginRepository loginRepository;

	private LoginEntity loginEntity;

	public LoginWorker(LoginRepository loginRepository2) {
		// TODO Auto-generated constructor stub
	}

	public LoginEntity getLoginEntity(String username) {
		return loginEntity = loginRepository.findByUsername(username);
	}

	public LoginEntity getLoginEntity() {
		return loginEntity;
	}

	public void setLoginEntity(LoginEntity loginEntity) {
		this.loginEntity = loginEntity;
	}

	public LoginEntity save(LoginEntity loginEntity) {
		return loginRepository.save(loginEntity);
	}
}

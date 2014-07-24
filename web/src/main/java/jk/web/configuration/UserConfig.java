package jk.web.configuration;

import jk.web.user.repository.LoginRepository;
import jk.web.user.repository.UserRepository;
import jk.web.workers.LoginWorker;
import jk.web.workers.UserWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private UserRepository userRepository;

	@Bean
	public UserWorker getUserWorker(){
		return new UserWorker(userRepository, getLoginWorker());
	}

	@Bean
	public LoginWorker getLoginWorker() {
		return new LoginWorker(loginRepository);
	}
}

package jk.web.user.services;

import jk.web.user.LoginDetails;
import jk.web.user.entities.LoginEntity;
import jk.web.user.repository.LoginRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class LoginDetailsServiceImpl implements UserDetailsService {

	private final Logger logger = LogManager.getLogger();
	private final LoginRepository repository;

	@Autowired
	public LoginDetailsServiceImpl(LoginRepository loginRepository) {
		this.repository = loginRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEMail) throws UsernameNotFoundException {
		logger.entry(usernameOrEMail);
		LoginEntity loginEntity = repository.findByUsernameOrEMail(usernameOrEMail);
		logger.trace("\n\t{}", loginEntity);
		if(loginEntity==null)
			throw new UsernameNotFoundException("No user found with username: " + usernameOrEMail);

		return LoginDetails
							.getBuilder()
							.setId(loginEntity.getId())
							.setUsername(loginEntity.getUsername())
							.setPassword(loginEntity.getPassword())
							.setPermissions(loginEntity.getPermissions())
							.build();
	}

}

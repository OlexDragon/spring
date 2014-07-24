package jk.web.user.services;

import jk.web.user.LoginDetails;
import jk.web.user.entities.LoginEntity;
import jk.web.user.repository.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class LoginDetailsServiceImpl implements UserDetailsService {

	private final LoginRepository repository;

	@Autowired
	public LoginDetailsServiceImpl(LoginRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginEntity loginEntity = repository.findByUsername(username);
		if(loginEntity==null)
            throw new UsernameNotFoundException("No user found with username: " + username);

		LoginDetails loginDetails = LoginDetails
										.getBuilder()
										.setId(loginEntity.getId())
										.setUsername(loginEntity.getUsername())
										.setPassword(loginEntity.getPassword())
										.setPermissions(loginEntity.getPermissions())
										.build();
		return loginDetails;
	}

}

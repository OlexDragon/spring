package jk.web.user;

import java.util.Collection;

import jk.web.user.repository.LoginRepository;
import jk.web.user.repository.LoginRepository.Permission;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginDetails implements UserDetails{//or SocialUser
	private static final long serialVersionUID = -6350018225137443424L;

	private final Logger logger = LogManager.getLogger();

	private Long id;
	private String username;
    private String password;
    private Permission permissions;

  public Long getId() {
		return logger.exit(id);
	}

	@Override
	public String getPassword() {
		return logger.exit(password);
	}

	@Override
	public String getUsername() {
		return logger.exit(username);
	}

	@Override
	public boolean isAccountNonExpired() {
		return logger.exit(!LoginRepository.Permission.hasPermission(permissions, LoginRepository.Permission.ACCOUNT_EXPIRED));
	}

	@Override
	public boolean isAccountNonLocked() {
		return logger.exit(!LoginRepository.Permission.hasPermission(permissions, LoginRepository.Permission.LOCKED));
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return logger.exit(!LoginRepository.Permission.hasPermission(permissions, LoginRepository.Permission.CREDENTIALS_EXPIRED));
	}

	@Override
	public boolean isEnabled() {
		return logger.exit(!LoginRepository.Permission.hasPermission(permissions, LoginRepository.Permission.DISABLED));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return logger.exit(LoginRepository.Permission.toPermissions(permissions.toLong()));
	}
//
//	public Long getPermissions() {
//		return logger.exit(permissions);
//	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public static class Builder{


		private final Logger logger = LogManager.getLogger();

		private Long id;
		private String username;
	    private String password;
	    private Permission permissions;

	    public Builder setId(Long id) {
	    	logger.entry(id);
			this.id = id;
			return this;
		}
		public Builder setUsername(String username) {
	    	logger.entry(username);
			this.username = username;
			return this;
		}
		public Builder setPassword(String password) {
	    	logger.entry(password);
			this.password = password;
			return this;
		}
		public Builder setPermissions(Permission permission) {
	    	logger.entry(permission);
			this.permissions = permission;
			return this;
		}

		public LoginDetails build() {
			LoginDetails loginDetails = new LoginDetails();

			loginDetails.id 		= id;
			loginDetails.username 	= username;
			loginDetails.password	= password;
			loginDetails.permissions= permissions;

			return loginDetails;
		}
	}
}

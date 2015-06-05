package jk.web.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginView {

	@NotNull
	@Size(max = 64)
	private String login;
	@NotNull
	@Size(max = 64)
	private String password;
	private boolean rememberMe;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
}

package jk.web.beans.view;

import javax.validation.constraints.Size;

public class SignUpView {

	@Size(min=1, max=65)
	private String username;
	@Size(min=1, max=65)
	private String password;
	@Size(min=1, max=65)
	private String confirmPassword;
	@Size(min=5, max=254)
	private String email;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "\n\tSignUpView [username=" + username + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", email=" + email
				+ "]";
	}
}

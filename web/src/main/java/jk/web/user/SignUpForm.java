package jk.web.user;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

public class SignUpForm{

	public enum Gender{
		MALE,
		FEMALE
	}

	@NotNull
	@Size(min=6, max = 64, message="SignUpForm.between_6_and_64_characters")
	private String username;

    @NotNull
    @Size(min=6, max = 64, message="SignUpForm.between_6_and_64_characters")
    private String password;

    @NotNull
    @Size(min=6, max = 64, message="SignUpForm.between_6_and_64_characters")
    private String repassword;

    @NotNull
    @Size(min=1, max = 164)
    private String firstName;

    @NotNull
    @Size(min=1, max = 164)
    private String lastName;

    @NotNull
    @Email
    @Size(min=5, max = 254, message="Please write a valid email address")
    private String eMail;

    @NotNull
    @DateTimeFormat(pattern="MM/dd/yyyy")
    private Date birthday;

    @NotNull
    private Gender sex;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Gender getSex() {
		return sex;
	}

	public void setSex(Gender sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "SignUpForm [username=" + username + ", password=" + password + ", repassword=" + repassword + ", firstName=" + firstName + ", lastName=" + lastName + ", eMail="
				+ eMail + ", birthday=" + birthday + ", sex=" + sex + "]";
	}
}

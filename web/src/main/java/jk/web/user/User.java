package jk.web.user;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jk.web.entities.user.TitleEntity;
import jk.web.entities.user.repositories.TitleRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;

public class User{

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private TitleRepository titleRepository;

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

    private TitleEntity title;
    
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
    @Size(min=1, max = 164)
    private String professionalSkill;

    @NotNull
    @Size(min=1, max = 164)
    private String workplace;

    private Integer birthYear;
    private Integer birthMonth;
    private Integer birthDay;

    @NotNull
    private Gender sex;

	private String newPassword;

	public List<TitleEntity> titles;

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public TitleEntity getTitle() {
		return title;
	}

	public void setTitle(TitleEntity title) {
		logger.entry(title);
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public User setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public User setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getRepassword() {
		return repassword;
	}

	public User setRepassword(String repassword) {
		this.repassword = repassword;
		return this;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public User setNewPassword(String newPassword) {
		this.newPassword = newPassword;
		return this;
	}

	public String getEMail() {
		return eMail;
	}

	public User setEMail(String eMail) {
		this.eMail = eMail;
		return this;
	}

	public Gender getSex() {
		return sex;
	}

	public User setSex(Gender sex) {
		this.sex = sex;
		return this;
	}
	public String getProfessionalSkill() {
		return professionalSkill;
	}

	public void setProfessionalSkill(String professionalSkill) {
		this.professionalSkill = professionalSkill;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer year) {
		logger.entry(year);
		this.birthYear = year;
	}

	public Integer getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(Integer month) {
		logger.entry(month);
		this.birthMonth = month;
	}

	public Integer getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Integer day) {
		this.birthDay = day;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", repassword=" + repassword + ", title=" + title + ", firstName=" + firstName + ", lastName="
				+ lastName + ", eMail=" + eMail + ", professionalSkill=" + professionalSkill + ", workplace=" + workplace + ", birthYear=" + birthYear + ", birthMonth="
				+ birthMonth + ", birthDay=" + birthDay + ", sex=" + sex + ", newPassword=" + newPassword + ", titles=" + titles + "]";
	}
}

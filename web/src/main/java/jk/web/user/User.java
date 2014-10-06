package jk.web.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.Email;

public class User{

	private final Logger logger = LogManager.getLogger();

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
    @Size(min=1, max = 164)
    private String professionalSkill;

    @NotNull
    @Size(min=1, max = 164)
    private String workplace;

    private String birthYear;
    private String birthMonth;
    private String birthDay;

    @NotNull
    private Gender sex;

	private String newPassword;
	private String address;
	private String region;
	private String country;

	private String city;

	private String postalCode;

	private String regionName;

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
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

	public String getCountry() {
		return country;
	}

	public User setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public User setAddress(String address) {
		this.address = address;
		return this;
	}
	public String getRegion() {
		return region;
	}

	public User setRegion(String region) {
		this.region = region;
		return this;
	}

	public String getCity() {
		return city;
	}

	public User setCity(String city) {
		this.city = city;
		return this;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public User setPostalCode(String postalCode) {
		this.postalCode = postalCode;
		return this;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionName() {
		return regionName;
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

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String year) {
		logger.entry(year);
		if(year!=null && !(year.isEmpty() || Character.isDigit(year.charAt(0))))
			this.birthYear = null;
		else
			this.birthYear = year;
	}

	public String getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(String month) {
		logger.entry(month);
		if(month!=null && !(month.isEmpty() || Character.isDigit(month.charAt(0))))
			this.birthMonth = null;
		else
			this.birthMonth = month;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String day) {
		logger.entry(day);
		if(day!=null && !(day.isEmpty() || Character.isDigit(day.charAt(0))))
			this.birthDay = null;
		else
			this.birthDay = day;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", repassword=" + repassword + ", firstName=" + firstName + ", lastName=" + lastName + ", eMail=" + eMail
				+ ", professionalSkill=" + professionalSkill + ", workplace=" + workplace + ", year=" + birthYear + ", month=" + birthMonth + ", day=" + birthDay + ", sex=" + sex
				+ ", newPassword=" + newPassword + ", address=" + address + ", region=" + region + ", country=" + country + ", city=" + city + ", postalCode=" + postalCode
				+ ", regionName=" + regionName + "]";
	}
}

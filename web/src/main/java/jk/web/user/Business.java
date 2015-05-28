package jk.web.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Business extends User {

	public static final int POST_MAX_SIZE = 250;
	@NotNull
	@Size(min=8, max = 245)
	private String site;
	@NotNull
	@Size(max=3, min=3)
	private String countryOfActivity;
	@Size(max = POST_MAX_SIZE, message="SignUpForm.between_6_and_64_characters")
	private String post;
	private String condition;
	private String address1;
	private String address2;
	private String city;
	private String country;
	private String postalcode;
	private String confirmEmail;
	private String vatNumber;
	private String company;
	private String phone;

	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getCountryOfActivity() {
		return countryOfActivity;
	}
	public void setcountryOfActivity(String countryOfActivity) {
		this.countryOfActivity = countryOfActivity;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getVatNumber() {
		return vatNumber;
	}
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getConfirmEmail() {
		return confirmEmail;
	}
	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "Business [site=" + site + ", countryOfActivity=" + countryOfActivity + ", post=" + post + ", condition=" + condition + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", country=" + country + ", postalcode=" + postalcode + ", confirmEmail=" + confirmEmail + ", vatNumber="
				+ vatNumber + ", company=" + company + ", phone=" + phone + ", getUsername()=" + getUsername() + ", getTitle()=" + getTitle() + ", getFirstName()="
				+ getFirstName() + ", getLastName()=" + getLastName() + ", getPassword()=" + getPassword() + ", getRepassword()=" + getRepassword()
				+ ", getNewPassword()=" + getNewPassword() + ", getEMail()=" + getEMail() + ", getSex()=" + getSex() + ", getProfessionalSkill()="
				+ getProfessionalSkill() + ", getWorkplace()=" + getWorkplace() + ", getBirthYear()=" + getBirthYear() + ", getBirthMonth()=" + getBirthMonth()
				+ ", getBirthDay()=" + getBirthDay() + "]";
	}

}

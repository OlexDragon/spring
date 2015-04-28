package jk.web.view.components;

import javax.validation.constraints.Size;

public class ContactUsForm {

	@Size(min=3, max=45)
	private String referenceNumber;
	@Size(min=3, max=245)
	private String name;
	@Size(min=5, max=254)
	private String email;
	@Size(min=3, max=145)
	private String subject;
	@Size(min=3, max=1000)
	private String message;

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ContactUsView [referenceNumber=" + referenceNumber + ", name="
				+ name + ", email=" + email + ", subject=" + subject
				+ ", message=" + message + "]";
	}
}

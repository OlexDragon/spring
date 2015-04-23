package jk.web.view.components;

public class ContactUsView {

	private String referenceNumber;
	private String name;
	private String email;
	private String subject;
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

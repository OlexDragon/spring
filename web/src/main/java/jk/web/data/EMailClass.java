package jk.web.data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class EMailClass {

	@NotNull
	@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")
	private String eMail;

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	@Override
	public String toString() {
		return "EMail [eMail=" + eMail + "]";
	}
}

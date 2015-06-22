package jk.web.controllers.rest.objects;

import javax.validation.constraints.Size;

public class UserRest{

	@Size(min=1, max=164)
	private String first_name;
	@Size(min=1, max=164)
	private String last_name;

	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
}

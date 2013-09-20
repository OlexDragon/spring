package irt.web.validators;

import irt.web.form.PartNumberForm;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PartNumberFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(PartNumberForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("target - "+target);
		System.out.println("errors - "+errors);
		PartNumberForm partNumberForm = (PartNumberForm) target; 
	}

}

package irt.web.validators;

import irt.web.form.ManufactureForm;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ManufactureValidaror implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(ManufactureForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("validate manufacture");
	}

}

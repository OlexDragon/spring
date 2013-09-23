package irt.web.validators;

import irt.web.editors.ComponentTypeEditor;
import irt.web.form.ManufactureForm;
import irt.web.form.PartNumberForm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IRTValidator implements Validator {

	Logger logger = LogManager.getLogger(ComponentTypeEditor.class.getName());

	private PartNumberValidator partNumberValidator = new PartNumberValidator();
	private ManufactureValidaror manufactureValidaror = new ManufactureValidaror();
	private Validator validator;

	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("supports:"+clazz);

		System.out.println("supports:"+clazz.getSimpleName());
		System.out.println("setPartNumberValidator:"+partNumberValidator);
		System.out.println("setManufactureValidaror:"+manufactureValidaror);
		boolean hasValidator = true;
		if(clazz.isAssignableFrom(PartNumberForm.class))
			validator = partNumberValidator;
		else if(clazz.isAssignableFrom(ManufactureForm.class))
			validator = manufactureValidaror;
		else
			hasValidator = false;

		return hasValidator;
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.info("validate:target"+target+"; errors:"+errors);
		validator.validate(target, errors); 
	}
}

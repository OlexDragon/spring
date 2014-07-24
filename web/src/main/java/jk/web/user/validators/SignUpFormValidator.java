package jk.web.user.validators;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import jk.web.user.SignUpForm;
import jk.web.user.repository.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SignUpFormValidator implements Validator {

	private final Logger logger = LogManager.getLogger();

	private static final String USERNAME_PATTERN = "^[A-Za-z0-9_-]{6,64}$";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

	private UserRepository userRepository;

	public SignUpFormValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SignUpForm signUpForm = (SignUpForm) target;

		usernameValidation(errors, signUpForm);
		firstAndSecondNamesValidation(errors, signUpForm);
		passwordValidation(errors, signUpForm);
		birthdayValidation(errors, signUpForm);
		eMailValidation(errors, signUpForm);
		genderValidation(errors, signUpForm);
	}

	private void usernameValidation(Errors errors, SignUpForm signUpForm) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "SignUpFormValidator.between_6_and_64_characters");

		String username = signUpForm.getUsername();
		if(errors.getFieldError("username")==null)
			if(username.length()>64 || username.length()<6 || !Pattern.matches(USERNAME_PATTERN, username))
				errors.rejectValue("username", "SignUpFormValidator.user_name_already_exists", "Username.");
			else if(userRepository.exists(username))
				errors.rejectValue("username", "SignUpFormValidator.user_name_already_exists", "This username already exists.");
		
	}

	private void firstAndSecondNamesValidation(Errors errors, SignUpForm signUpForm) {
		final String[] fieldsNames = new String[]{"firstName", "lastName"};
		final String[] names = new String[]{signUpForm.getFirstName(), signUpForm.getLastName()};

		for(int fieldIndex=0; fieldIndex<fieldsNames.length; fieldIndex++){
			String fieldName = fieldsNames[fieldIndex];
			String name = names[fieldIndex];
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "SignUpFormValidator.this_field_must_be_filled");
			if(errors.getFieldError(fieldName)==null && name.length()>164)
				errors.rejectValue(fieldName, "SignUpForm.name_is_to_Long_max_164", "Name is to Long (max = 164)");
		}
	}

	private void passwordValidation(Errors errors, SignUpForm signUpForm) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "SignUpFormValidator.between_6_and_64_characters");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repassword", "SignUpFormValidator.between_6_and_64_characters");

		String password = signUpForm.getPassword();
		String repassword = signUpForm.getRepassword();
		logger.trace("password'{}' : repassword'{}'", password, repassword);

		if(errors.getFieldError("password")==null && (password.length()>64 || password.length()<6))
			errors.rejectValue("password", "SignUpForm.between_6_and_64_characters", "Between 6 and 64 characters.");

		if(errors.getFieldError("repassword")==null)
			if(repassword.length()>64 || repassword.length()<6)
				errors.rejectValue("repassword", "SignUpForm.between_6_and_64_characters", "Between 6 and 64 characters.");
			else if(!password.equals(repassword)){
				errors.rejectValue("repassword", "SignUpFormValidator.these_passwords_dont_match", "These passwords do not match.");
				errors.rejectValue("password", "SignUpFormValidator.these_passwords_dont_match", "These passwords do not match.");
		}
	}

	private void eMailValidation(Errors errors, SignUpForm signUpForm) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eMail", "SignUpFormValidator.please_write_a_valid_email_address");
		if(errors.getFieldError("eMail")==null){
			String eMail = signUpForm.getEMail();
			if(!emailPattern.matcher(eMail).matches())
				errors.rejectValue("eMail", "SignUpFormValidator.please_write_a_valid_email_address", "Not valid");
			else if(userRepository.existsEMail(eMail))
				errors.rejectValue("eMail", "SignUpFormValidator.this_email_already_exists", "Exists");
		}
	}

	private void birthdayValidation(Errors errors, SignUpForm signUpForm) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthday", "SignUpFormValidator.this_field_must_be_filled");

		Date birthday = signUpForm.getBirthday();
		if(errors.getFieldError("birthday")==null){
			Calendar cal = Calendar.getInstance();
			if(birthday.after(cal.getTime()))
				errors.rejectValue("birthday", "SignUpFormValidator.cant_be_born_in_the_future", "Can't be born in the future");
			
			else{
				cal.add(Calendar.YEAR, -16);
				if(birthday.after(cal.getTime()))
					errors.rejectValue("birthday", "SignUpFormValidator.you_are_very_young", "You are very young.");
			}
		}
	}

	private void genderValidation(Errors errors, SignUpForm signUpForm) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sex", "SignUpFormValidator.make_a_choice");
	}
}

package jk.web.user.validators;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import jk.web.user.Business;
import jk.web.user.User;
import jk.web.user.entities.EMailEntity;
import jk.web.user.entities.EMailEntity.EMailStatus;
import jk.web.workers.UserWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SignUpFormValidator implements Validator {

	private final Logger logger = LogManager.getLogger();

	private Map<String, Integer>  usernameRange;
	private Map<String, Integer>  passwordRange;

	private String USERNAME_PATTERN;
	private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
											//  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

	@Autowired
	private UserWorker userWorker;

	public SignUpFormValidator( Map<String, Integer>  usernameRange,  Map<String, Integer>  passwordRange) {
		this.usernameRange = usernameRange;
		this.passwordRange = passwordRange;
		USERNAME_PATTERN = "^[A-Za-z0-9_-]{"+ usernameRange.get("min")+ ","+ usernameRange.get("max")+ "}$";
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.entry(target);
		User user = (User) target;

		usernameValidation(errors, user);
		fieldValidation("firstName", user.getFirstName(), errors);
		fieldValidation("lastName", user.getLastName(), errors);
		passwordValidation(errors, user);
		birthdayValidation(errors, user);
		professionalSkillValidation(errors, user.getProfessionalSkill(), "professionalSkill");
		professionalSkillValidation(errors, user.getWorkplace(), "workplace");
		eMailValidation(errors, user.getEMail());
		genderValidation(errors, user);
		if(target instanceof Business)
			validate((Business)target, errors);
	}

	private void validate(Business business, Errors errors){
		siteAddrValidation(business.getSite(), errors);
		eMailValidation(errors, business);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "post", "SignUpFormValidator.this_field_must_be_filled");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "SignUpFormValidator.this_field_must_be_filled");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address1", "SignUpFormValidator.this_field_must_be_filled");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalcode", "SignUpFormValidator.this_field_must_be_filled");
	}

	private void eMailValidation(Errors errors, Business business) {
		String eMail = business.getEMail();
		String confirmEmail = business.getConfirmEmail();
		logger.trace("\n\t eMail = {}\n\t confirmEmail = {}", eMail, confirmEmail);
		if(errors.getFieldError("eMail")==null && !eMail.equals(confirmEmail)){
			errors.rejectValue("eMail", "SignUpFormValidator.these_X_dont_match", new String[]{"e-mails"}, "These passwords do not match.");
			errors.rejectValue("confirmEmail", "SignUpFormValidator.these_X_dont_match", new String[]{"e-mails"}, "These passwords do not match.");
		}
	}

	private void siteAddrValidation(String site, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "site", "SignUpFormValidator.this_field_must_be_filled");

		if(errors.getFieldError("professionalSkill")==null){
			site = site.toLowerCase();
			if(site.startsWith("http") || site.length()<10)
				errors.rejectValue("site", "SignUpFormValidator.this_field_must_be_filled");
		}
	}

	private void professionalSkillValidation(Errors errors, String fieldValue, String fieldName) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "SignUpFormValidator.between_min_and_max_characters", new Integer[]{1, 164});

		if(errors.getFieldError("professionalSkill")==null){
			if(fieldValue.length()>164)
				errors.rejectValue(fieldName, "SignUpFormValidator.between_min_and_max_characters", new Integer[]{1, 164}, "Between {0} and {1} characters.");
		}
	}

	public boolean fieldValidation(String fieldName, String value, Errors errors) {
		logger.entry(fieldName, value);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "SignUpFormValidator.this_field_must_be_filled");
		if(errors.getFieldError(fieldName)==null && value.length()>164)
				errors.rejectValue(fieldName, "SignUpForm.name_is_to_Long_max_164", "Name is to Long (max = 164)");

		return logger.exit(errors.getFieldError(fieldName)==null);// no error
	}

	public void usernameValidation(Errors errors, User user) {

		Integer min = usernameRange.get("min");
		Integer max = usernameRange.get("max");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "SignUpFormValidator.between_min_and_max_characters", new Integer[]{min, max});

		if(errors.getFieldError("username")==null){
			String username = user.getUsername();
			logger.entry(username);
			if(username.length()<min)
				errors.rejectValue("username", "SignUpFormValidator.username_min", new Integer[]{min}, "The Username you provided must have at least {0} characters.");
			else if(username.length()>max || !Pattern.matches(USERNAME_PATTERN, username))
				errors.rejectValue("username", "SignUpFormValidator.between_min_and_max_characters", new Integer[]{min, max}, "Between {0} and {1} characters.");
			else if(userWorker.existsUserName(username))
				errors.rejectValue("username", "SignUpFormValidator.user_name_already_exists", new String[]{username}, "This username already exists.");
		}
		
	}

	public boolean passwordValidation(Errors errors, User user) {

		final String fieldNamePassword = "newPassword";
		Integer min = passwordRange.get("min");
		Integer max = passwordRange.get("max");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldNamePassword, "SignUpFormValidator.password_min", new Integer[]{min});
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldNamePassword, "SignUpFormValidator try_another");

		final String fieldNameConfirm = "repassword";
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldNameConfirm, "SignUpFormValidator.password_min", new Integer[]{min});
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldNameConfirm, "SignUpFormValidator try_another");

		String newPassword = user.getNewPassword();
		String repassword = user.getRepassword();
		logger.trace("\n\tnewPassword=\t'{}'\n\trepassword\t'{}'", newPassword, repassword);

		if(errors.getFieldError(fieldNamePassword)==null)
			if(newPassword.length()<min){
				errors.rejectValue(fieldNamePassword, "SignUpFormValidator.password_min", new Integer[]{min}, "Between {0} and {1} characters.");
				errors.rejectValue(fieldNamePassword, "SignUpFormValidator try_another");
			}else if(newPassword.length()>max)
				errors.rejectValue(fieldNamePassword, "SignUpFormValidator.between_min_and_max_characters", new Integer[]{min, max}, "Between {0} and {1} characters.");

		if(errors.getFieldError(fieldNameConfirm)==null && !newPassword.equals(repassword)){
			errors.rejectValue(fieldNameConfirm, "SignUpFormValidator.these_X_dont_match", new String[]{"passwords"}, "These passwords do not match.");
			errors.rejectValue(fieldNamePassword, "SignUpFormValidator.these_X_dont_match", new String[]{"passwords"}, "These passwords do not match.");
		}
		return logger.exit(errors.getFieldError(fieldNamePassword)==null && errors.getFieldError(fieldNameConfirm)==null);// no error
	}

	public boolean eMailValidation(Errors errors, String eMail) {
		logger.entry("\n\t", eMail, "\n\t", userWorker);

		final String fieldName = "eMail";
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "SignUpFormValidator.please_write_a_valid_email_address");
		if(errors.getFieldError(fieldName)==null){
			if(emailPattern.matcher(eMail).matches()){
				EMailEntity eMailEntity = userWorker.getEMail(eMail);
				if(eMailEntity!=null && (eMailEntity.getStatus()==EMailStatus.ACTIVE || eMailEntity.getStatus()==EMailStatus.TO_CONFIRM))
					errors.rejectValue(fieldName, "SignUpFormValidator.this_email_already_exists", "Exists");
			}else{
				logger.trace("\n\t email '{}' does not mach the patern:\n\t{}", eMail, emailPattern);
				errors.rejectValue(fieldName, "SignUpFormValidator.please_write_a_valid_email_address", "Not valid");
			}
		}
		return logger.exit(errors.getFieldError(fieldName)==null);// no error
	}

	public boolean birthdayValidation(Errors errors, User user){
		logger.entry(user);
	
		final String fieldName = "birthYear";
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "SignUpFormValidator.this_field_must_be_filled");

		if(errors.getFieldError(fieldName)==null){
			Integer month = user.getBirthMonth();
			Integer day = user.getBirthDay();
			if(month==null || day==null)
				errors.rejectValue(fieldName, "SignUpFormValidator.this_field_must_be_filled");
			else{
//				try {
					Date birthday = UserWorker.parseBirthday( user.getBirthYear(), user.getBirthMonth(), user.getBirthDay());
					Calendar cal = Calendar.getInstance();
					if(birthday.after(cal.getTime()))
						errors.rejectValue(fieldName, "SignUpFormValidator.cant_be_born_in_the_future", "Can't be born in the future");
			
//					else{
//						cal.add(Calendar.YEAR, -16);
//						if(birthday.after(cal.getTime()))
//							errors.rejectValue(fieldName, "SignUpFormValidator.you_are_very_young", "You are very young.");
//					}
//				} catch (ParseException e) {
//					errors.rejectValue(fieldName, "SignUpFormValidator.this_field_must_be_filled");
//					logger.catching(e);
//				}
			}
		}else
			logger.trace("\n\tThe feald 'Year' is empty.");

		return logger.exit(errors.getFieldError(fieldName)==null);// no error
	}

	public boolean genderValidation(Errors errors, User user) {
		String fieldName = "sex";
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "SignUpFormValidator.make_a_choice");
		return logger.exit(errors.getFieldError(fieldName)==null);// no error
	}

	public Map<String, Integer> getUsernameRange() {
		return usernameRange;
	}

	public Map<String, Integer> getPasswordRange() {
		return passwordRange;
	}
}

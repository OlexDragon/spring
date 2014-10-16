package jk.web.user.controllers;

import java.security.Principal;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jk.web.user.User;
import jk.web.user.User.Gender;
import jk.web.user.entities.AddressEntity;
import jk.web.user.entities.CountryEntity;
import jk.web.user.validators.SignUpFormValidator;
import jk.web.workers.AddressWorker;
import jk.web.workers.FileWorker;
import jk.web.workers.UserWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {


	private final Logger logger = LogManager.getLogger();

	@Autowired
	private UserWorker userWorker;
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AddressWorker addressWorker;
	@Autowired
	private FileWorker fileWorker;

	@RequestMapping(value="/user", method=RequestMethod.GET)
	public String user(User user, Principal principal){

		String username = principal.getName();
		logger.entry(username);

		userWorker.setUser(username, user);
		return "user";
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "submit_edit_username")
	public String editUsername(User user, Principal principal, Model model, BindingResult bindingResults, HttpServletRequest request, HttpServletResponse response){

		String username = principal.getName();

		String un = user.getUsername();
		final String attributeName = "edit_username";
		if(un==null)
			model.addAttribute(attributeName, true);
		else{
			String password = user.getPassword();
			if(passwordMatches(username, password, attributeName, model, bindingResults)){
				signUpFormValidator.usernameValidation(bindingResults, user);
				if(bindingResults.hasErrors()){
					logger.debug("Username '{}' can not be updated by '{}'.", username, un);
					model.addAttribute(attributeName, true);
				}else{
					if(!username.equals(un)){
						userWorker.saveNewUsername(un);
						logger.debug("Username '{}' updated by'{}'.", username, un);

						LoginController.logout(request);

						return "redirect:/login";
					}else
						bindingResults.rejectValue("username", "UserController.username_has_not_change", "Username.");
				}
			}
		}

		userWorker.fillUser(user);
		userWorker.fillUserAddress(user);
		model.addAttribute("showP", true);
		return "user";
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "submit_edit_password")
	public String editPassword(User user, Principal principal, Model model, BindingResult bindingResults, HttpServletRequest request){

		String username = principal.getName();

		String attributeName = "edit_password";
		String password = user.getPassword();
		if(passwordMatches(username, password, attributeName, model, bindingResults)){
			if(signUpFormValidator.passwordValidation(bindingResults, user)){
				userWorker.saveNewPassword(user.getNewPassword());

				LoginController.logout(request);

				return "redirect:/login";
			}else
				model.addAttribute(attributeName, true);
		}

		userWorker.fillUser(user);
		userWorker.fillUserAddress(user);
		model.addAttribute("showP", true);
		return "user";
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "submit_edit_firstName")
	public String editFirstName(User user, Principal principal, Model model, BindingResult bindingResults){

		String username = principal.getName();
		logger.entry(username);

		final String firstName = user.getFirstName();
		final String attributeName = "edit_firstName";
		if(firstName==null)
			model.addAttribute(attributeName, true);
		else{
			if(signUpFormValidator.fieldValidation("firstName", firstName, bindingResults))
				if(userWorker.isValid())
					userWorker.saveFirstName(username, firstName);
				else{
					userWorker.setFirstName(username, firstName);
					if(!saveIfValid())
						bindingResults.rejectValue("firstName", "UserController.fill_missing_fields");
				}
			else
				model.addAttribute(attributeName, true);
		}

		userWorker.fillUser(user);
		userWorker.fillUserAddress(user);
		model.addAttribute("showP", true);
		return "user";
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "submit_edit_lastName")
	public String editLastName(User user, Principal principal, Model model, BindingResult bindingResults){

		String username = principal.getName();

		String lastName = user.getLastName();
		final String attributeName = "edit_lastName";
		if(lastName==null)
			model.addAttribute(attributeName, true);
		else{
			if(signUpFormValidator.fieldValidation("lastName", lastName, bindingResults))
				if(userWorker.isValid())
					userWorker.saveLastName(username, lastName);
				else{
					userWorker.setLastName(username, lastName);
					if(!saveIfValid())
						bindingResults.rejectValue("lastName", "UserController.fill_missing_fields");
				}
			else
				model.addAttribute(attributeName, true);
		}

		userWorker.fillUser(user);
		userWorker.fillUserAddress(user);
		model.addAttribute("showP", true);
		return "user";
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "submit_edit_eMail")
	public String editEMail(User user, Principal principal, Model model, Errors bindingResults){

		String username = principal.getName();

		String eMail = user.getEMail();
		final String attributeName = "edit_eMail";
		if(eMail==null)
			model.addAttribute(attributeName, true);
		else{
			eMail = eMail.toLowerCase();
			if(signUpFormValidator.eMailValidation(bindingResults, user.getEMail()))
				if(userWorker.isValid())
					userWorker.saveEMail(username, eMail);
				else{
					userWorker.setEMail(username, eMail);
					if(!saveIfValid())
						bindingResults.rejectValue("eMail", "UserController.fill_missing_fields");
				}
			else
				model.addAttribute(attributeName, true);
		}

		userWorker.fillUser(user);
		userWorker.fillUserAddress(user);
		model.addAttribute("showP", true);
		return "user";
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "submit_edit_gender")
	public String editGender(User user, Principal principal, Model model, Errors bindingResults){

		String username = principal.getName();

		Gender gender = user.getSex();
		final String attributeName = "edit_gender";
		if(gender==null)
			model.addAttribute(attributeName, true);
		else{
			if(signUpFormValidator.genderValidation(bindingResults, user))
				if(userWorker.isValid())
					userWorker.saveGender(username, gender);
				else{
					userWorker.setGender(username, gender);
					if(!saveIfValid())
						bindingResults.rejectValue("sex", "UserController.fill_missing_fields");
				}
			else
				model.addAttribute(attributeName, true);
		}

		userWorker.fillUser(user);
		userWorker.fillUserAddress(user);
		model.addAttribute("showP", true);
		return "user";
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "submit_edit_birthday")
	public String editBirthday(User user, Principal principal, Model model, Errors bindingResults){
		logger.entry(user);

		String username = principal.getName();

		Integer year = user.getBirthYear();
		Integer month = user.getBirthMonth();
		Integer day = user.getBirthDay();

		try{
			final String attributeName = "edit_birthday";
			if(year==null && month==null && day==null)
				model.addAttribute(attributeName, true);
			else{
				if(signUpFormValidator.birthdayValidation(bindingResults, user))
					if(userWorker.isValid()){
						userWorker.saveBirthday(username, year, month, day);
					}else{
						userWorker.setBirthday(username, year, month, day);
						if(!saveIfValid())
							bindingResults.rejectValue("birthYear", "UserController.fill_missing_fields");
					}
				else
					model.addAttribute(attributeName, true);
			}
		}catch(ParseException ex){
			bindingResults.rejectValue("birthYear", "UserController.fill_missing_fields");
			logger.catching(ex);
		}

		userWorker.fillUser(user);
		userWorker.fillUserAddress(user);
		model.addAttribute("showP", true);
		return "user";
	}

	private boolean saveIfValid(){
		boolean saved = false;
		if(userWorker.isValid())
			saved = userWorker.save()!=null;
		return saved;
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "submit_edit_address")
	public String editAddress(User user, Principal principal, Model model, BindingResult bindingResults, HttpServletRequest request){

		String username = principal.getName();
		AddressEntity addressEntity = userWorker.getAddressEntity(username);

		String attributeName = "edit_address";

		boolean edit = false;

		//Address
		String address = user.getAddress();
		if(address == null || address.isEmpty()){
			edit = true;
			if(addressEntity==null || addressEntity.getAddress()==null)
				bindingResults.rejectValue("address", "UserController.enter_address");
		}

		//City
		String city = user.getCity();
		if(city == null || city.isEmpty()){
			edit = true;
			if(addressEntity==null || addressEntity.getCity()==null)
				bindingResults.rejectValue("city", "UserController.enter_city");
		}

		//Postal code
		String postalCode = user.getPostalCode();
		if(postalCode == null || postalCode.isEmpty()){
			edit = true;
			if(addressEntity==null || addressEntity.getPostalCode()==null)
				bindingResults.rejectValue("postalCode", "UserController.enter_postal_code");
		}

		//Country
		String countryCode = user.getCountry();
		String region = user.getRegion();
		AddressEntity ae = userWorker.getAddressEntity();
		String uwCountryCode = ae!=null ? ae.getCountryCode() : null;
		CountryEntity ce = null;
		if(countryCode==null){
			if(ae!=null)
				ce = ae.getCountryEntity();
			addressWorker.setCountryCode(uwCountryCode);
			edit = true;
			if(addressEntity==null || addressEntity.getCountryCode()==null)
				bindingResults.rejectValue("country", "UserController.select_country");
		}else{
			addressWorker.setCountryCode(countryCode);
			if(uwCountryCode!=null && !countryCode.equals(uwCountryCode))
				edit = true;

			ce = addressWorker.getCountryEntity(countryCode);

			if(ce!=null && ce.getRegionName()!=null){
				if(region==null){
					edit = true;
					if(addressEntity==null || addressEntity.getRegionsCode()==null)
						bindingResults.rejectValue("region", "UserController.select_"+ce.getRegionName());
				}else{
					String uwRegionCode = ae!=null ? ae.getRegionsCode() : null;
					if(uwRegionCode!=null && !region.equals(uwRegionCode))
						edit = true;
				}
			}
		}

		logger.trace("\n\t"
				+ "address:\t'{}'\n\t"
				+ "city:\t'{}'\n\t"
				+ "postalCode:\t'{}'\n\t"
				+ "region:\t'{}'\n\t"
				+ "countryCode:\t'{}'",
				address,
				city,
				postalCode,
				region,
				countryCode);

		fileWorker.saveMap(userWorker.getUserEntity().getId(), user.getAddress(), user.getCity(), user.getRegion(), ce!=null ? ce.getCountryName() : null, user.getPostalCode());
		user.setRegionName(ce!=null ? ce.getRegionName() : null);

		if(edit){
			model.addAttribute(attributeName, true);
		}else{
			userWorker.saveAddress(new AddressEntity()
										.setAddress(address)
										.setCity(city)
										.setCountryCode(addressWorker.getCountryCode())
										.setPostalCode(postalCode)
										.setRegionsCode(region));
		}

		userWorker.fillUser(user);
		model.addAttribute("addressWorker", addressWorker);
		model.addAttribute("showAH", true);
		return "user";
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "submit_user_img")
	public String addUserImage(User user, Principal principal, Model model, BindingResult bindingResults, HttpServletRequest request){
		
		model.addAttribute("addressWorker", addressWorker);
		return "user";
	}

	private boolean passwordMatches(String username, String password, String attributeName, Model model, BindingResult bindingResults){

		boolean matches = false;
		if(password==null || password.isEmpty()){

			bindingResults.rejectValue("password", "UserController.enter_the_password", "Enter_the password.");
			model.addAttribute(attributeName, true);

		}else if(passwordEncoder.matches(password, userWorker.getPassword(username)))
			matches = true;
		else{

			bindingResults.rejectValue("password", "UserController.wrong_password");
			model.addAttribute(attributeName, true);
		}

		return matches;
	}
}

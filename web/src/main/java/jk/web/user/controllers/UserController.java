package jk.web.user.controllers;

import java.security.Principal;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jk.web.user.Address;
import jk.web.user.Address.AddressType;
import jk.web.user.User;
import jk.web.user.User.Gender;
import jk.web.user.entities.AddressEntity;
import jk.web.user.entities.CountryEntity;
import jk.web.user.repository.TitleRepository;
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
	@Autowired
	private TitleRepository titleRepository;

	@RequestMapping(value="/user", method=RequestMethod.GET)
	public String user(Principal principal, Model model){

		String username = principal.getName();

		User user = new User();
		userWorker.fillUser(user);
		model.addAttribute("user", user);

		Address homeAddress = resetAddress(username, model, AddressType.HOME);
		homeAddress.setEditAddress(false);
		Address workAddress = resetAddress(username, model, AddressType.WORK);
		workAddress.setShowAddress(false);

		logger.trace("\n\thomeAddress = {}\n\tworkAddress = {}", homeAddress, workAddress);
		return "user";
	}

	public Address resetAddress(String username, Model model, AddressType addressType) {
		Address address = new Address(addressType);
		userWorker.filllUserAddress(username, address);
		model.addAttribute(addressType+"_Addr", address);
		return address;
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "cancel_profile")
	public String cancelProfile(Principal principal, Model model){

		String username = principal.getName();

		User user = new User();
		userWorker.fillUser(user);
		model.addAttribute("user", user);
		model.addAttribute("showP", true);

		Address homeAddress = resetAddress(username, model, AddressType.HOME);
		Address workAddress = resetAddress(username, model, AddressType.WORK);

		logger.trace("\n\thomeAddress = {}\n\tworkAddress = {}", homeAddress, workAddress);
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

		model.addAttribute("showP", true);
		userWorker.fillUser(user);

		Address homeAddress = new Address(AddressType.HOME);
		userWorker.filllUserAddress(username, homeAddress);
		model.addAttribute(AddressType.HOME+"_Addr", homeAddress);

		Address workAddress = new Address(AddressType.WORK);
		userWorker.filllUserAddress(username, workAddress);
		model.addAttribute(AddressType.WORK+"_Addr", workAddress);

		return logger.exit("user");
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
		Address homeAddress = new Address(AddressType.HOME);
		userWorker.filllUserAddress(username, homeAddress);
		model.addAttribute(AddressType.HOME+"_Addr", homeAddress);

		Address workAddress = new Address(AddressType.WORK);
		userWorker.filllUserAddress(username, workAddress);
		model.addAttribute(AddressType.WORK+"_Addr", workAddress);

		model.addAttribute("showP", true);
		return logger.exit("user");
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "submit_edit_title")
	public String editTitlee(User user, Principal principal, Model model){

		String username = principal.getName();

		Integer titleId = user.getTitleId();
		final String attributeName = "edit_title";
		if(titleId==null)
			model.addAttribute(attributeName, true);
		else if(userWorker.isValid())
			userWorker.saveTitle(username, titleId);
		else
			userWorker.setTitle(username, titleId);

		userWorker.fillUser(user);
		Address homeAddress = new Address(AddressType.HOME);
		userWorker.filllUserAddress(username, homeAddress);
		model.addAttribute(AddressType.HOME+"_Addr", homeAddress);

		Address workAddress = new Address(AddressType.WORK);
		userWorker.filllUserAddress(username, workAddress);
		model.addAttribute(AddressType.WORK+"_Addr", workAddress);

		model.addAttribute("showP", true);
		return logger.exit("user");
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
		Address homeAddress = new Address(AddressType.HOME);
		userWorker.filllUserAddress(username, homeAddress);
		model.addAttribute(AddressType.HOME+"_Addr", homeAddress);

		Address workAddress = new Address(AddressType.WORK);
		userWorker.filllUserAddress(username, workAddress);
		model.addAttribute(AddressType.WORK+"_Addr", workAddress);

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
		Address homeAddress = new Address(AddressType.HOME);
		userWorker.filllUserAddress(username, homeAddress);
		model.addAttribute(AddressType.HOME+"_Addr", homeAddress);

		Address workAddress = new Address(AddressType.WORK);
		userWorker.filllUserAddress(username, workAddress);
		model.addAttribute(AddressType.WORK+"_Addr", workAddress);

		model.addAttribute("showP", true);
		return logger.exit("user");
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
		Address homeAddress = new Address(AddressType.HOME);
		userWorker.filllUserAddress(username, homeAddress);
		model.addAttribute(AddressType.HOME+"_Addr", homeAddress);

		Address workAddress = new Address(AddressType.WORK);
		userWorker.filllUserAddress(username, workAddress);
		model.addAttribute(AddressType.WORK+"_Addr", workAddress);

		model.addAttribute("showP", true);
		return logger.exit("user");
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
		Address homeAddress = new Address(AddressType.HOME);
		userWorker.filllUserAddress(username, homeAddress);
		model.addAttribute(AddressType.HOME+"_Addr", homeAddress);

		Address workAddress = new Address(AddressType.WORK);
		userWorker.filllUserAddress(username, workAddress);
		model.addAttribute(AddressType.WORK+"_Addr", workAddress);

		model.addAttribute("showP", true);
		return logger.exit("user");
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

		Address homeAddress = new Address(AddressType.HOME);
		userWorker.filllUserAddress(username, homeAddress);
		model.addAttribute(AddressType.HOME+"_Addr", homeAddress);

		Address workAddress = new Address(AddressType.WORK);
		userWorker.filllUserAddress(username, workAddress);
		model.addAttribute(AddressType.WORK+"_Addr", workAddress);

		model.addAttribute("showP", true);
		return logger.exit("user");
	}

	private boolean saveIfValid(){
		boolean saved = false;
		if(userWorker.isValid())
			saved = userWorker.save()!=null;
		return saved;
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "cancel_addr_WORK")
	public String cancelWorkAddress(Address address, Principal principal, Model model){

		String username = principal.getName();

		User user = new User();
		userWorker.fillUser(user);
		model.addAttribute("user", user);

		Address homeAddress = resetAddress(username, model, AddressType.HOME);
		Address workAddress = resetAddress(username, model, AddressType.WORK);
		workAddress.setShowAddress(true);

		logger.trace("\n\thomeAddress = {}\n\tworkAddress = {}", homeAddress, workAddress);
		return "user";
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "cancel_addr_HOME")
	public String cancelHomeAddress(Address address, Principal principal, Model model){

		String username = principal.getName();

		User user = new User();
		userWorker.fillUser(user);
		model.addAttribute("user", user);

		Address homeAddress = resetAddress(username, model, AddressType.HOME);
		Address workAddress = resetAddress(username, model, AddressType.WORK);
		homeAddress.setShowAddress(true);

		logger.trace("\n\thomeAddress = {}\n\tworkAddress = {}", homeAddress, workAddress);
		return "user";
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "sbmt_addr_WORK")
	public String editWorkAddress(Address address, Principal principal, Model model){

		String username = principal.getName();

		Address homeAddress = new Address(AddressType.HOME);
		userWorker.filllUserAddress(username, homeAddress);
		model.addAttribute(AddressType.HOME+"_Addr", homeAddress);

		AddressEntity addressEntity = userWorker.getAddressEntity(username, AddressType.WORK);
		address.setAddressType(AddressType.WORK);

		return editAddress(username, addressEntity, address, new Address(AddressType.WORK), model);
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "sbmt_addr_HOME")
	public String editHomeAddress(Address address, Principal principal, Model model){

		String username = principal.getName();

		Address workAddress = new Address(AddressType.WORK);
		userWorker.filllUserAddress(username, workAddress);
		model.addAttribute(AddressType.WORK+"_Addr", workAddress);

		AddressEntity addressEntity = userWorker.getAddressEntity(username, AddressType.HOME);
		address.setAddressType(AddressType.HOME);

		return editAddress(username, addressEntity, address, new Address(AddressType.HOME), model);
	}

	public String editAddress(String username, AddressEntity addressEntity, Address address, Address modelAddress, Model model){

		String aeStr = addressEntity!=null ? addressEntity .getAddress() : null;
		String addressStr = address.getAddress();
		Boolean isError = checkAdress(aeStr, addressStr);
		if(isError!=null){
			modelAddress.setEditAddress(true);
			if(isError)
				modelAddress.setAddressError("address.enter_address");
			else if(addressStr==null)
				address.setAddress(aeStr);
		}

		aeStr = addressEntity!=null ? addressEntity.getCity() : null;
		addressStr = address.getCity();
		isError = checkAdress(aeStr, addressStr);
		if(isError!=null){
			modelAddress.setEditAddress(true);
			if(isError)
				modelAddress.setCityError("address.enter_city");
			else if(addressStr==null)
				address.setCity(aeStr);
		}

		aeStr = addressEntity!=null ? addressEntity.getPostalCode() : null;
		addressStr = address.getPostalCode();
		isError = checkAdress(aeStr, addressStr);
		if(isError!=null){
			modelAddress.setEditAddress(true);
			if(isError)
				modelAddress.setPostalCodeError("address.enter_postal_code");
			else if(addressStr==null)
				address.setPostalCode(aeStr);
		}

		aeStr = addressEntity!=null ? addressEntity.getCountryCode() : null;
		addressStr = address.getCountryCode();
		isError = checkAdress(aeStr, addressStr);
		if(isError!=null){
			modelAddress.setEditAddress(true);
			if(isError)
				modelAddress.setCountryCodeError("address.select_country");
			else if(addressStr==null){
				addressStr = aeStr;
				address.setCountryCode(aeStr);
			}
		}
		CountryEntity countryEntity = null;
		if(addressStr!=null && !addressStr.isEmpty()){
			countryEntity = addressWorker.getCountryEntity(addressStr);
			if(countryEntity!=null){
				String regionName = countryEntity.getRegionName();
				modelAddress.setRegionName(regionName);
				if(regionName!=null){
					modelAddress.setRegions(addressWorker.getRegionEntities(addressStr));

					
					aeStr = addressEntity!=null ? addressEntity.getRegionsCode() : null;
					addressStr = address.getRegionCode();
					isError = checkAdress(aeStr, addressStr);
					if(isError!=null){
						modelAddress.setEditAddress(true);
						if(isError)
							modelAddress.setRegionCodeError("address.select_"+regionName);
						else if(addressStr==null){
							address.setRegionCode(aeStr);
						}
					}
				}
			}
		}

		Long userId = userWorker.getUserEntity().getId();
		AddressType addressType = modelAddress.getAddressType();
		fileWorker.saveMap(
						fileWorker.getMapFile(userId, addressType),
						address.getAddress(),
						address.getCity(),
						address.getRegionCode(),
						countryEntity!=null ? countryEntity.getCountryName() : null,
						modelAddress.getPostalCode()
		);
		modelAddress.setMapPath(fileWorker.getMapFileUrl(addressType, userId));

		if(modelAddress.isEditAddress())
			model.addAttribute("addressWorker", addressWorker);
		else{
			if(!userWorker.saveAddress(new AddressEntity()
												.setType(address.getAddressType())
												.setAddress(address.getAddress())
												.setCity(address.getCity())
												.setCountryCode(address.getCountryCode())
												.setPostalCode(address.getPostalCode())
												.setRegionsCode(address.getRegionCode()))){
				modelAddress.setAddressError("address.fill_profile");
			}
			modelAddress = address;
		}

		modelAddress.setShowAddress(true);

		User user = new User();
		userWorker.fillUser(user);
		model.addAttribute("user", user);
		model.addAttribute(addressType+"_Addr", modelAddress);

		logger.trace("\n\tEXIT\n\t"
				+ "username\t{}\n\t"
				+ "addressEntity\t{}\n\t"
				+ "address:\t'{}'\n\t"
				+ "modelAddress:\t{}",
				username,
				addressEntity,
				address,
				modelAddress);

		return "user";
	}

	/**
	 * 
	 * @param entityString
	 * @param modelString
	 * @return	true if entityString and modelString == null<br>
	 * 			false if modelString ==null and entityString !=null<br>
	 * 			null if modelString !=null
	 */
	private Boolean checkAdress(String entityString, String modelString) {
		Boolean isError;
		if(modelString == null || modelString.isEmpty()){
			if(entityString==null || entityString.isEmpty())
				isError = true;
			else
				isError = false;
		}else
			isError = null;

		return isError;
	}

	@RequestMapping(value="/user", method=RequestMethod.POST, params = "submit_user_img")
	public String addUserImage(User user, Principal principal, Model model, BindingResult bindingResults, HttpServletRequest request){
		
		model.addAttribute("addressWorker", addressWorker);
		return logger.exit("user");
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

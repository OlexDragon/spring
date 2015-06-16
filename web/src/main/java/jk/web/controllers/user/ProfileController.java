package jk.web.controllers.user;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jk.web.entities.user.FileEntity;
import jk.web.repositories.user.FileRepositiry;
import jk.web.repositories.user.TitleRepository;
import jk.web.user.Address;
import jk.web.user.User;
import jk.web.user.User.Gender;
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
public class ProfileController {

	private final Logger logger = LogManager.getLogger();

	private static final String S_E_USERNAME = "s_e_username";
	private static final String S_E_PASSWORD = "s_e_password";
	private static final String S_E_PROFILE = "s_e_profile";

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
	private FileRepositiry fileRepositiry;
	@Autowired
	private TitleRepository titleRepository;

//	@RequestMapping(value="/profile", method=RequestMethod.GET)
//	public String profile(Principal principal, Model model){
//
//		resetUser(model, principal.getName(), new User());
//		resetFilesList(model);
//
//		resetAddress(model, new Address(AddressType.HOME));
//		resetAddress(model, new Address(AddressType.WORK));
//
//		return "profile";
//	}

//	@RequestMapping(value="/profile/edit", method=RequestMethod.GET)
//	public String profileEditPage(Principal principal, Model model){
//		model.addAttribute("edit_profile", true);
//		return profile(principal, model);
//	}
//
//	@RequestMapping(value="/profile/edit", method=RequestMethod.POST, params = "cancel_profile")
//	public String cancelProfile(Principal principal, Model model){
//		model.addAttribute("edit_profile", true);
//		model.addAttribute("showP", true);
//		return profile(principal, model);
//	}

//	@RequestMapping(value="/profile/edit", method=RequestMethod.POST, params = S_E_PROFILE)
//	public String editProfile(User user, Principal principal, Model model, BindingResult bindingResults){
//
//		logger.trace("\n\t{}\n\t{}", user);
//
//		userWorker.setTitle(user.getTitle());
//		editFirstName(user, model, bindingResults);
//		editLastName(user, model, bindingResults);
//		editGender(user, model, bindingResults);
//		editBirthday(user,model, bindingResults);
//
//		if(!bindingResults.hasErrors() && userWorker.isValid())
//			userWorker.save();
//
//		userWorker.fillUser(user);
//
//		resetAddress(model, new Address(AddressType.HOME));
//		resetAddress(model, new Address(AddressType.WORK));
//
//		model.addAttribute("profileImageLink", fileWorker.getProfileImage(userWorker.getUserEntity()));
//		model.addAttribute("showP", true);
//		FormsController.signupAttributes(model, titleRepository);
//		model.addAttribute("edit_profile", true);
//		return "profile";
//	}

//	@RequestMapping(value="/profile/edit", method=RequestMethod.POST, params = S_E_USERNAME)
//	public String editUsername(User user, Principal principal, Model model, BindingResult bindingResults, HttpServletRequest request){
//		final String attributeName = S_E_USERNAME;
//
//		String username = principal.getName();
//		String eMail = userWorker.getEMail();
//		logger.trace("\n\tuserWorker.getEMail():\t'{}'\n\t{}", eMail, user);
//
//		String un = user.getUsername();
//		String em = user.getEMail();
//
//		if(un==null && em==null)
//			model.addAttribute(attributeName, true);
//		else{
//			String password = user.getPassword();
//			if(passwordMatches(username, password, attributeName, model, bindingResults)){
//
//				if(un==null || !un.equals(username))
//					signUpFormValidator.usernameValidation(bindingResults, user);
//				if(em==null || !em.equals(eMail))
//					signUpFormValidator.eMailValidation(bindingResults, em);
//
//				if(bindingResults.hasErrors()){
//					logger.debug("Username '{}' can not be updated by '{}'.", username, un);
//					model.addAttribute(attributeName, true);
//				}else{
//
//					if(em!=null && !em.equals(eMail)){
//						logger.debug("E-Mail '{}' updated by'{}'.", eMail, em);
//						userWorker.saveEMail(username, em);
//					}
//
//					if(!username.equals(un)){
//						userWorker.saveNewUsername(un);
//						logger.debug("Username '{}' updated by'{}'.", username, un);
//
//						try { request.logout(); } catch (ServletException e) { logger.catching(e); }
//
//						return "redirect:/login";
//					}else
//						bindingResults.rejectValue("username", "UserController.username_has_not_change", "Username.");
//				}
//			}
//		}
//
//		model.addAttribute("showP", true);
//		userWorker.fillUser(user);
//
//		resetAddress(model, new Address(AddressType.HOME));
//		resetAddress(model, new Address(AddressType.WORK));
//
//		model.addAttribute("profileImageLink", fileWorker.getProfileImage(userWorker.getUserEntity()));
//		model.addAttribute("edit_profile", true);
//		return "profile";
//	}
//
//	@RequestMapping(value="/profile/edit", method=RequestMethod.POST, params = S_E_PASSWORD)
//	public String editPassword(User user, Principal principal, Model model, BindingResult bindingResults, HttpServletRequest request){
//		String attributeName = S_E_PASSWORD;
//
//		logger.trace("\n\t{}", user);
//		String username = principal.getName();
//
//		String password = user.getPassword();
//		if(passwordMatches(username, password, attributeName, model, bindingResults)){
//			if(signUpFormValidator.passwordValidation(bindingResults, user)){
//				userWorker.saveNewPassword(user.getNewPassword());
//
//				try { request.logout(); } catch (ServletException e) { logger.catching(e); }
//
//				return "redirect:/login";
//			}else
//				model.addAttribute(attributeName, true);
//		}
//
//		userWorker.fillUser(user);
//
//		resetAddress(model, new Address(AddressType.HOME));
//		resetAddress(model, new Address(AddressType.WORK));
//
//		model.addAttribute("profileImageLink", fileWorker.getProfileImage(userWorker.getUserEntity()));
//		model.addAttribute("showP", true);
//		model.addAttribute("edit_profile", true);
//		return "profile";
//	}

//	@RequestMapping(value="/profile/edit", method=RequestMethod.POST, params = "cancel_edit_addr")
//	public String cancelEditAddress(Principal principal, Model model){
//
//		String username = principal.getName();
//
//		User user = new User();
//		resetUser(model, username, user);
//
//		resetAddress(model, new Address(AddressType.HOME));
//		resetAddress(model, new Address(AddressType.WORK));
//
//		model.addAttribute("edit_profile", true);
//		model.addAttribute("showA", true);
//		return "profile";
//	}
//
//	@RequestMapping(value="/profile/edit", method=RequestMethod.POST, params = "sbmt_addr_WORK")
//	public String editWorkAddress(Address address, Principal principal, Model model){
//		logger.entry();
//
//		User user = new User();
//		resetUser(model, principal.getName(), user);
//
//		resetAddress(model, new Address(AddressType.HOME));
//		resetAddress(model, address.setAddressType(AddressType.WORK));
//
//		String countryCode = address.getCountryCode();
//		if(countryCode!=null)
//			model.addAttribute("regions", addressWorker.getRegionEntities(countryCode));
//
//		model.addAttribute("edit_profile", true);
//		model.addAttribute("showA", true);
//
//		address.setEditAddress(true);
//		return "profile";
//	}
//
//	@RequestMapping(value="/profile/edit", method=RequestMethod.POST, params = "sbmt_addr_HOME")
//	public String editHomeAddress(Address address, Principal principal, Model model){
//		logger.entry();
//
//		User user = new User();
//		resetUser(model, principal.getName(), user);
//
//		resetAddress(model, address.setAddressType(AddressType.HOME));
//		resetAddress(model, new Address(AddressType.WORK));
//
//		String countryCode = address.getCountryCode();
//		if(countryCode!=null)
//			model.addAttribute("regions", addressWorker.getRegionEntities(countryCode));
//
//		model.addAttribute("edit_profile", true);
//		model.addAttribute("showA", true);
//
//		address.setEditAddress(true);
//		return "profile";
//	}

//	@RequestMapping(value="/profile/edit", method=RequestMethod.POST, params = "sv_addr_WORK")
//	public String saveWorkAddress(Address address, Principal principal, Model model){
//		logger.entry(address);
//
//		Address homeAddress = new Address(AddressType.HOME);
//		userWorker.fillUserAddress(principal.getName(), homeAddress);
//		model.addAttribute(AddressType.HOME+"_Addr", homeAddress);
//
//		AddressEntity addressEntity = userWorker.getAddressEntity(AddressType.WORK);
//		address.setAddressType(AddressType.WORK);
//
//		return editAddress(addressEntity, address, new Address(AddressType.WORK), model);
//	}
//
//	@RequestMapping(value="/profile/edit", method=RequestMethod.POST, params = "sv_addr_HOME")
//	public String saveHomeAddress(Address address, Principal principal, Model model){
//		logger.entry(address);
//
//		Address workAddress = new Address(AddressType.WORK);
//		userWorker.fillUserAddress(principal.getName(), workAddress);
//		model.addAttribute(AddressType.WORK+"_Addr", workAddress);
//
//		AddressEntity addressEntity = userWorker.getAddressEntity(AddressType.HOME);
//		address.setAddressType(AddressType.HOME);
//
//		return editAddress(addressEntity, address, new Address(AddressType.HOME), model);
//	}

	public void resetAddress(String username, Model model, Address address) {
		userWorker.fillUserAddress(username, address);
		model.addAttribute(address.getAddressType()+"_Addr", address);
	}

	public void resetAddress(Model model, Address address) {
		userWorker.fillUserAddress(address);
		model.addAttribute(address.getAddressType()+"_Addr", address);
	}

//	public String editAddress(AddressEntity addressEntity, Address address, Address modelAddress, Model model){
//		logger.entry( addressEntity, address);
//
//		//Address
//		String aeStr = addressEntity!=null ? addressEntity .getAddress() : null;
//		String addressStr = address.getAddress();
//		Boolean isError = checkAdress(aeStr, addressStr);
//		if(isError!=null){
//			modelAddress.setEditAddress(true);
//			if(isError)
//				modelAddress.setAddressError("address.enter_address");
//			else if(addressStr==null)
//				address.setAddress(aeStr);
//		}
//
//		//City
//		aeStr = addressEntity!=null ? addressEntity.getCity() : null;
//		addressStr = address.getCity();
//		isError = checkAdress(aeStr, addressStr);
//		if(isError!=null){
//			modelAddress.setEditAddress(true);
//			if(isError)
//				modelAddress.setCityError("address.enter_city");
//			else if(addressStr==null)
//				address.setCity(aeStr);
//		}
//
//		//Postal code
//		aeStr = addressEntity!=null ? addressEntity.getPostalCode() : null;
//		addressStr = address.getPostalCode();
//		isError = checkAdress(aeStr, addressStr);
//		if(isError!=null){
//			modelAddress.setEditAddress(true);
//			if(isError)
//				modelAddress.setPostalCodeError("address.enter_postal_code");
//			else if(addressStr==null)
//				address.setPostalCode(aeStr);
//		}
//
//		//Country
//		aeStr = addressEntity!=null ? addressEntity.getCountryCode() : null;
//		addressStr = address.getCountryCode();
//		isError = checkAdress(aeStr, addressStr);
//		if(isError!=null){
//			modelAddress.setEditAddress(true);
//			if(isError)
//				modelAddress.setCountryCodeError("address.select_country");
//			else if(addressStr==null){
//				addressStr = aeStr;
//				address.setCountryCode(aeStr);
//			}
//		}
//		CountryEntity countryEntity = null;
//		if(addressStr!=null && !addressStr.isEmpty()){
////			countryEntity = addressWorker.getCountryEntity(addressStr);
////			if(countryEntity!=null){
////				String regionName = countryEntity.getRegionName();
////				modelAddress.setRegionName(regionName);
////				if(regionName!=null){
////					model.addAttribute("regions", addressWorker.getRegionEntities(addressStr));
////
////					//Region
////					aeStr = addressEntity!=null ? addressEntity.getRegion() : null;
////					addressStr = address.getRegion();
////					isError = checkAdress(aeStr, addressStr);
////					if(isError!=null){
////						modelAddress.setEditAddress(true);
////						if(isError)
////							modelAddress.setRegionCodeError("address.select_"+regionName);
////						else if(addressStr==null){
////							address.setRegion(aeStr);
////						}
////					}
////				}
////			}
//		}
//
//		Long userID = userWorker.getUserEntity().getId();
//		AddressType addressType = modelAddress.getAddressType();
//		Thread t =fileWorker.saveMap(
//						fileWorker.getMapFile(userID, addressType),
//						address.getAddress(),
//						address.getCity(),
//						address.getRegion(),
//						countryEntity!=null ? countryEntity.getCountryName() : null,
//						modelAddress.getPostalCode()
//		);
//		modelAddress.setMapPath(fileWorker.getMapFileUrl(addressType, userID));
//
//		if(modelAddress.isEditAddress())
//			model.addAttribute("addressWorker", addressWorker);
//		else{
//			if(userWorker.saveAddress(new AddressEntity()
//												.setAddress(address.getAddress())
//												.setCity(address.getCity())
//												.setCountryCode(address.getCountryCode())
//												.setPostalCode(address.getPostalCode())
//												.setRegionsCode(address.getRegion()))){
//
//			}
//			address.setRegionName(modelAddress.getRegionName());
//			modelAddress = address;
//		}
//
//		model.addAttribute("showA", true);
//
//		User user = new User();
//		resetUser(model, addressStr, user);
//		model.addAttribute(addressType+"_Addr", modelAddress);
//
//		logger.trace("\n\tEXIT\n\t"
//				+ "addressEntity\t{}\n\t"
//				+ "address:\t'{}'\n\t"
//				+ "modelAddress:\t{}",
//				addressEntity,
//				address,
//				modelAddress);
//
//		try {
//			t.join();
//		} catch (InterruptedException e) {
//			logger.catching(e);
//		}
//
//		address.setMapPath(fileWorker.getMapFileUrl(address.getAddressType(), userID));
//		model.addAttribute("edit_profile", true);
//		return "profile";
//	}

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

	@RequestMapping(value="/profile", method=RequestMethod.POST, params = "s_upload_img")
	public String addUserImage(User user, Principal principal, Model model, BindingResult bindingResults, HttpServletRequest request){
		
		model.addAttribute("addressWorker", addressWorker);
		model.addAttribute("edit_profile", true);
		return "profile";
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

//	public void resetUser(Model model, String username, User user) {
//		userWorker.fillUser(user);
//		model.addAttribute("user", user);
//		model.addAttribute("profileImageLink", fileWorker.getProfileImage(userWorker.getUserEntity()));
//	}

	private void resetFilesList(Model model) {

		Long userID = userWorker.getUserEntity().getId();
		List<FileEntity> fileEntities;

		if(model.containsAttribute("edit_profile"))
			fileEntities = fileRepositiry.findByUserID(userID);
		else
			fileEntities = fileRepositiry.findByUserIDAndShowToAll(userID, true);

		if(fileEntities!=null)
			model.addAttribute("files", fileEntities);
	}

	public void editFirstName(User user, Model model, BindingResult bindingResults){

		final String firstName = user.getFirstName();
		if(firstName==null)
			model.addAttribute(S_E_PROFILE, true);
		else{
			if(signUpFormValidator.fieldValidation("firstName", firstName, bindingResults))
				userWorker.setFirstName(firstName);
			else
				model.addAttribute(S_E_PROFILE, true);
		}
	}

	public void editLastName(User user, Model model, BindingResult bindingResults){

		String lastName = user.getLastName();
		if(lastName==null)
			model.addAttribute(S_E_PROFILE, true);
		else{
			if(signUpFormValidator.fieldValidation("lastName", lastName, bindingResults))
				userWorker.setLastName(lastName);
			else
				model.addAttribute(S_E_PROFILE, true);
		}
	}

	public void editGender(User user, Model model, Errors bindingResults){

		Gender gender = user.getSex();
		if(gender==null)
			model.addAttribute(S_E_PROFILE, true);
		else{
			if(signUpFormValidator.genderValidation(bindingResults, user))
				userWorker.setGender(gender);
			else
				model.addAttribute(S_E_PROFILE, true);
		}
	}

	public void editBirthday(User user, Model model, Errors bindingResults){

		Integer year = user.getBirthYear();
		Integer month = user.getBirthMonth();
		Integer day = user.getBirthDay();

		try{
			if(year==null && month==null && day==null)
				model.addAttribute(S_E_PROFILE, true);
			else{
				if(signUpFormValidator.birthdayValidation(bindingResults, user))
					userWorker.setBirthday(year, month, day);
				else
					model.addAttribute(S_E_PROFILE, true);
			}
		}catch(ParseException ex){
			bindingResults.rejectValue("birthYear", "UserController.fill_missing_fields");
			logger.catching(ex);
		}
	}
}

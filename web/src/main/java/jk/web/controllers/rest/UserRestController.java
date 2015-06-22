package jk.web.controllers.rest;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Calendar;

import jk.web.entities.user.LoginEntity;
import jk.web.entities.user.UserEntity;
import jk.web.entities.user.repositories.LoginRepository;
import jk.web.entities.user.repositories.UserRepository;
import jk.web.user.User.Gender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/user")
public class UserRestController {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LoginRepository loginRepository;

	@RequestMapping(produces="application/json;charset=UTF-8", method=RequestMethod.GET)
	public UserEntity gerUserEntity(@RequestParam(required=false) String username, @RequestParam(required=false) String password, Principal principal){

		UserEntity userEntity;
		if((username=authenticatedUsername(username, password, principal))==null)
			userEntity = new UserEntity(0L, "Anonymous", "is not Authenticated", new Timestamp(Calendar.getInstance().getTime().getTime()), null);

		else{
			userEntity = userRepository.findOneByLoginEntityUsername(username);
			if(userEntity==null){
				LoginEntity loginEntity = loginRepository.findOneByUsername(username);
				if(loginEntity!=null){
					userEntity = new UserEntity(loginEntity.getId());
					userEntity.setLoginEntity(loginEntity);
				}else{
					userEntity = new UserEntity(0L);
				}
			}
		}
		return logger.exit(userEntity);
	}

	@RequestMapping(produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public UserEntity updateUser(
			@RequestParam(required=false) String username,
			@RequestParam(required=false) String password,
			@RequestParam(required=false) String firstName,
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) Gender gender,
			Principal principal){
		logger.entry(username, password, firstName, lastName, principal);

		UserEntity userEntity;
		if((username=authenticatedUsername(username, password, principal))==null)
			userEntity = new UserEntity(0L, "Anonymous", "is not Authenticated", new Timestamp(Calendar.getInstance().getTime().getTime()), null);
		else{
			userEntity = userRepository.findOneByLoginEntityUsername(username);
			logger.trace("userRepository.findOneByLoginEntityUsername({}) = {}", username, userEntity);
			if(userEntity==null){
				LoginEntity loginEntity = loginRepository.findOneByUsername(username);
				userEntity = new UserEntity(loginEntity.getId());
			}

			if(firstName!=null && !(firstName = firstName.trim()).isEmpty())
				userEntity.setFirstName(firstName);

			if(lastName!=null && !(lastName = lastName.trim()).isEmpty())
				userEntity.setLastName(lastName);

			if(gender!=null)
				userEntity.setGender(gender);

			if(userEntity.getFirstName()!=null && userEntity.getLastName()!=null)
				userEntity = userRepository.save(userEntity);
			else
				userEntity = new UserEntity(0L, "Can not be null", "Can not be null", new Timestamp(Calendar.getInstance().getTime().getTime()), null);
		}

		return logger.exit(userEntity);
	}

	private String authenticatedUsername(String username, String password, Principal principal) {
		if(username==null){
			if(principal!=null)
				username = principal.getName();
		}else{
			if(password==null)
				username = null;
			else
				if(!passwordMatcher(username, password))
					username = null;
		}
		return username;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	private boolean passwordMatcher(String username, String password){

		boolean matches;
		if(password==null || password.isEmpty() || username==null || username.isEmpty())
			matches = false;

		else{
			LoginEntity loginEntity = loginRepository.findOneByUsername(username);
			if(loginEntity!=null)
				matches = passwordEncoder.matches(password, loginEntity.getPassword());
			else
				matches = false;
		}

		return matches;
	}

	@RequestMapping(value="genders", produces="application/json;charset=UTF-8")
	public Gender[] getGenders(){
		return Gender.values();
	}

	@RequestMapping(value="prof-skills", produces="application/json;charset=UTF-8")
	public Gender[] getProfessionalSkillEntity(){
		return Gender.values();
	}
}

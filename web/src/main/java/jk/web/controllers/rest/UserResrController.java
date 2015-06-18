package jk.web.controllers.rest;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Calendar;

import jk.web.entities.user.LoginEntity;
import jk.web.entities.user.UserEntity;
import jk.web.entities.user.repositories.LoginRepository;
import jk.web.entities.user.repositories.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/user")
public class UserResrController {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LoginRepository loginRepository;

	@RequestMapping(produces="application/json;charset=UTF-8")
	private UserEntity gerUserEntity(Principal principal){
		logger.entry(principal);

		UserEntity userEntity;
		if(principal==null){
			userEntity = new UserEntity(0L, "Anonymous", "is not Authenticated", new Timestamp(Calendar.getInstance().getTime().getTime()), null);
		}else{
			String username = principal.getName();
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
		logger.trace("\n\tuserEntity: ", userEntity);
		return userEntity;
	}
}

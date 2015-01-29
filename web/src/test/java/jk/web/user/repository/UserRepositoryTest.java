package jk.web.user.repository;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import jk.web.EmbeddedMySqlDataSourceConfig;
import jk.web.user.User;
import jk.web.user.User.Gender;
import jk.web.user.entities.UserEntity;
import jk.web.workers.UserWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { EmbeddedMySqlDataSourceConfig.class })
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
//@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class UserRepositoryTest {

	private final Logger logger = LogManager.getLogger();

	private static final String ALEXDRAGON = "alexdragon";

	@Autowired
	private UserWorker userWorker;

	@Test
	public void tesUserEntityt() {
		UserEntity userEntity = userWorker.getUserEntity(ALEXDRAGON);
		assertNotNull(userEntity);
	}

//	@Test
//	public void testCreateNewUser() throws ParseException{
//		User user = fillSignUpForm("username");
//		UserEntity newUser = userWorker.createNewUser(user);
//		assertNotNull(newUser);
//		logger.trace("\n\t{}", newUser);
//		assertEquals("username", newUser.getLoginEntity().getUsername());
//	}
//
	@Test
	public void testUpdateFields() throws ParseException{
//		logger.entry();
//		User user = fillSignUpForm("updateFirstName");
//		userWorker.createNewUser(user);
//		logger.trace(userWorker.getUserEntity());
//
//		userWorker.setFirstName("updateFirstName", "updateFirstName");
//		userWorker.setLastName("updateFirstName", "updateLastName");
//		Date birthday = new Date();
//		userWorker.setBirthday("updateFirstName", birthday);
//		userWorker.setEMail("updateFirstName", "updateEMail");
//		userWorker.setProfessionalSkill("professionalSkill");
//		userWorker.setWorkplace("workplace");
//		userWorker.saveGender("updateFirstName", Gender.MALE);
//		logger.trace(userWorker.getUserEntity());
//		logger.trace(userWorker.getUserEntity());
//
//		userWorker.setUserEntity(null);
//		UserEntity userEntity = userWorker.getUserEntity("updateFirstName");
//		logger.trace(userEntity);
//
//		assertEquals("updateFirstName", userEntity.getFirstName());
//		assertEquals("updateLastName", userEntity.getLastName());
//		assertEquals(Gender.MALE, userEntity.getGender());
//		List<EMailEntity> emails = userEntity.getEmails();
//		assertNotNull(emails);
//		assertEquals(2, emails.size());
	}

	private User fillSignUpForm(String username) {
		User user = new User();
		user.setUsername(username);
		user.setNewPassword("password");
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setBirthYear(1964);
		user.setBirthMonth(5);
		user.setBirthDay(13);
		user.setEMail("eMail");
		user.setSex(Gender.FEMALE);
		return user;
	}
}

package jk.web.entities.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import jk.web.TestHomeController;
import jk.web.entities.EmailEntity;
import jk.web.entities.user.UserEmailEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestHomeController.class)
public class ContactEmailRepositoryTest {

	public static final String EMAIL = "email";

	public static final UserEmailEntity contactEmailEntity;
	static{
		contactEmailEntity = new UserEmailEntity();
		contactEmailEntity.setEmail(EMAIL);
		
	}
	private final static Logger logger = LogManager.getLogger();

	@Autowired
	UserContactEmailRepository contactEmailRepository;

	@Test
	public void test() {
		EmailEntity cee = contactEmailRepository.save(contactEmailEntity);
		logger.trace(cee);
		assertNotNull(cee);

		cee = contactEmailRepository.findOne(cee.getEmailId());
		logger.trace(cee);
		assertNotNull(cee);
		assertEquals(EMAIL, cee.getEmail());
	}

}

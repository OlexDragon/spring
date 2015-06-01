package jk.web.entities.repositories;

import static org.junit.Assert.*;
import jk.web.TestHomeController;
import jk.web.entities.ContactEmailEntity;

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

	public static final ContactEmailEntity contactEmailEntity;
	static{
		contactEmailEntity = new ContactEmailEntity();
		contactEmailEntity.setEmail(EMAIL);
		
	}
	private final static Logger logger = LogManager.getLogger();

	@Autowired
	ContactEmailRepository contactEmailRepository;

	@Test
	public void test() {
		ContactEmailEntity cee = contactEmailRepository.save(contactEmailEntity);
		logger.trace(cee);
		assertNotNull(cee);

		cee = contactEmailRepository.findOne(cee.getEmailId());
		logger.trace(cee);
		assertNotNull(cee);
		assertEquals(EMAIL, cee.getEmail());
	}

}
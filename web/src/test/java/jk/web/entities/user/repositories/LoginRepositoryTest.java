package jk.web.entities.user.repositories;

import static org.junit.Assert.assertNotNull;
import jk.web.TestHomeController;
import jk.web.entities.user.LoginEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestHomeController.class)
public class LoginRepositoryTest {

	private final static Logger logger = LogManager.getLogger();

	@Autowired
	LoginRepository loginRepository;

	@Test
	public void test() {
		LoginEntity loginEntity = loginRepository.findOne(1l);
		logger.trace(loginEntity);

		assertNotNull(loginEntity);
	}

}

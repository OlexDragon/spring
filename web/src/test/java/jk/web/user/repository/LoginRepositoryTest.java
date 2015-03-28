package jk.web.user.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import jk.web.EmbeddedMySqlDataSourceConfig;
import jk.web.entities.user.LoginEntity;
import jk.web.repositories.user.LoginRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
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
public class LoginRepositoryTest {

	private final Logger logger = LogManager.getLogger();
	@Autowired
	private LoginRepository loginRepository;

	@Before
	public void init(){
		LoginEntity entity = new LoginEntity("Alex", "dragon");
		entity = loginRepository.save(entity);
		logger.trace("\n\t" + "Logins:{}", loginRepository.findAll());
		assertTrue(loginRepository.exists(entity.getId()));
	}

	@Test
	public void testLoginEntity() {
		LoginEntity loginEntity = loginRepository.findByUsername("Alex");
		loginEntity.setUsername("username");
		loginEntity = loginRepository.save(loginEntity);
		logger.trace("\n\t{}",loginEntity);
		assertEquals("username", loginEntity.getUsername());
	}
}

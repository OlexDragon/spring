package jk.web.user.repository;

import static org.junit.Assert.*;
import jk.web.EmbeddedMySqlDataSourceConfig;
import jk.web.workers.AddressWorker;

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
@Transactional
public class CountryRepositoryTest {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private AddressWorker addressWorker;

	@Test
	public void test() {
		logger.trace(addressWorker);
	}

}

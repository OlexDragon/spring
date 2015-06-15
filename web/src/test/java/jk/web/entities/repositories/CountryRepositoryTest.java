package jk.web.entities.repositories;

import static org.junit.Assert.*;

import java.util.List;

import jk.web.TestHomeController;
import jk.web.entities.CountryEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestHomeController.class)
@TestPropertySource(locations="classpath:mysql-test.properties")
public class CountryRepositoryTest {

	private final static Logger logger = LogManager.getLogger();

	@Autowired
	CountryRepository countryRepository;

	@Test
	public void test() {
		List<CountryEntity> countryEntities = countryRepository.findAll(new Sort("countryName"));
		logger.trace(countryEntities);

		assertNotNull(countryEntities);
		assertTrue(countryEntities.size()==250);
		assertNotEquals(countryEntities.get(0), countryEntities.get(1));
	}

}

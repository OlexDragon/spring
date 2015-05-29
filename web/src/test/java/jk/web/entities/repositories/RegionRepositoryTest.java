package jk.web.entities.repositories;

import static org.junit.Assert.*;

import java.util.List;

import jk.web.TestHomeController;
import jk.web.entities.RegionEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestHomeController.class)
public class RegionRepositoryTest {

	private final static Logger logger = LogManager.getLogger();

	@Autowired
	RegionRepository regionRepository;

	@Test
	public void test() {
		List<RegionEntity> findAll = regionRepository.findAll();
		assertNotNull(findAll);
		assertTrue(findAll.size()>10);
		logger.trace("\n\t{}", findAll);
	}

}

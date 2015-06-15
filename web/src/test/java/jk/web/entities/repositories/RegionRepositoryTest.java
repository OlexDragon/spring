package jk.web.entities.repositories;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import jk.web.HomeController;
import jk.web.controllers.rest.AddressRestTest;
import jk.web.entities.RegionEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HomeController.class)
public class RegionRepositoryTest {

	private final static Logger logger = LogManager.getLogger();

	@Autowired
	RegionRepository regionRepository;

	@Test
	public void getAll() {
		List<RegionEntity> findAll = regionRepository.findAll();
		assertNotNull(findAll);
		assertTrue(findAll.size()>10);
		logger.trace("\n\t{}", findAll);
	}

	@Test
	public void getCountryRegions(){
		List<RegionEntity> entities = regionRepository.findByCountryEntityGeonamesIdOrderByRegionNameAsc(AddressRestTest.GEONAME_ID);
		assertNotNull(entities);
		assertTrue(entities.size()>0);
	}
}

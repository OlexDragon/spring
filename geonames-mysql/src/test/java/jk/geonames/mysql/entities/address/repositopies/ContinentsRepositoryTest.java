package jk.geonames.mysql.entities.address.repositopies;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jk.GeonamesMySQLApp;
import jk.mysql.entities.address.ContinentEntity;
import jk.mysql.entities.address.CountryEntity;
import jk.mysql.entities.address.RegionEntity;
import jk.mysql.entities.address.RegionTitleEntity;
import jk.mysql.entities.address.repositopies.ContinentsRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GeonamesMySQLApp.class)
public class ContinentsRepositoryTest {

	private Logger logger = LogManager.getLogger();

	public static final ContinentEntity continentEntity = new ContinentEntity( "NA", "North Ametrica");
	public static final CountryEntity countryEntity = new CountryEntity( 11111L, "CA", "Canada", "CAN", "Ontario", "A1A");
	public static final RegionEntity regionEntity = new RegionEntity( 555L, "QC", "Quebec");
	public static final RegionTitleEntity regionTitleEntity = new RegionTitleEntity("State");

	@Autowired
	ContinentsRepository continentsRepository;

	@Test
	public void test() {
		List<CountryEntity> countryEntityList = new ArrayList<>();
		countryEntityList.add(countryEntity);
		continentEntity.setCountryEntityList(countryEntityList);
		countryEntity.setContinentEntity(continentEntity);

		List<RegionEntity> regionEntitiesList = new ArrayList<>();
		regionEntitiesList.add(regionEntity);
		countryEntity.setRegionEntityList(regionEntitiesList);
		regionEntity.setCountryEntity(countryEntity);

		List<RegionTitleEntity> regionTitleEntityList = new ArrayList<>();
		regionTitleEntityList.add(regionTitleEntity);
		countryEntity.setRegionTitleEntityList(regionTitleEntityList);
		regionTitleEntity.setCountryEntity(countryEntity);

		ContinentEntity continentEntity = continentsRepository.save(ContinentsRepositoryTest.continentEntity);
		logger.trace(continentEntity);

		// Test Result

		assertNotNull(continentEntity);
		continentEntity = continentsRepository.findOne(continentEntity.getContinentCode());
		logger.trace(continentEntity);
		assertEquals(ContinentsRepositoryTest.continentEntity.getContinentName(), continentEntity.getContinentName());

		countryEntityList = continentEntity.getCountryEntityList();
		assertTrue(countryEntityList.size()>0);
		CountryEntity countryEntity = countryEntityList.get(0);
		assertEquals(ContinentsRepositoryTest.countryEntity.getCountryName(), countryEntity.getCountryName());

		regionEntitiesList = countryEntity.getRegionEntityList();
		assertTrue(regionEntitiesList.size()>0);
		assertEquals(ContinentsRepositoryTest.regionEntity.getRegionName(), regionEntitiesList.get(0).getRegionName());

		regionTitleEntityList = countryEntity.getRegionTitleEntityList();
		assertTrue(regionTitleEntityList.size()>0);
		assertEquals(regionTitleEntity.getRegionTitle(), regionTitleEntityList.get(0).getRegionTitle());
	}

}

package jk.geonames.objects;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class CountryChildrenTest {

	private final Logger logger = LogManager.getLogger();

	@Test
	public void test() {
		RestTemplate restTemplate = new RestTemplate();
		CountryChildren countryChildren = restTemplate.getForObject(CountryChildren.URL, CountryChildren.class, 6251999, "olexdragon");
		logger.trace(countryChildren);

		assertNotNull(countryChildren);
		assertNotNull(countryChildren.getCountryChildrenList());
		assertTrue(countryChildren.getCountryChildrenList().size()>0);
	}

}

package jk.service;

import javax.annotation.PostConstruct;

import jk.geonames.objects.CountriesInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeonamesToMySQLService {

	private final Logger logger = LogManager.getLogger();

	@Value("${geonames.username}")
	private String username;

	@PostConstruct
	public void copyGeonamesToMysql(){
		logger.entry();

		RestTemplate restTemplate = new RestTemplate();
//		logger.trace(restTemplate.getForObject(CountriesInfo.URL, String.class, username));
		CountriesInfo countriesInfo = restTemplate.getForObject(CountriesInfo.URL, CountriesInfo.class, username);
		logger.trace("{}", countriesInfo);
	}
}

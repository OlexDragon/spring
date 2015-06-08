package jk.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import jk.geonames.objects.CountriesInfo;
import jk.geonames.objects.CountryChild;
import jk.geonames.objects.CountryChildren;
import jk.geonames.objects.CountryInfo;
import jk.mysql.entities.address.ContinentEntity;
import jk.mysql.entities.address.CountryEntity;
import jk.mysql.entities.address.RegionEntity;
import jk.mysql.entities.address.repositopies.ContinentsRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeonamesToMySQLService {

	private final Logger logger = LogManager.getLogger();

	@Value("${geonames.username}")
	private String username;

	@Autowired
	private ContinentsRepository continentsRepository;

	@PostConstruct
	public void copyGeonamesToMysql(){
		logger.entry();

		//Get Country Info
		RestTemplate restTemplate = new RestTemplate();
		CountriesInfo countriesInfo = restTemplate.getForObject(CountriesInfo.URL, CountriesInfo.class, username);
		logger.trace("{}", countriesInfo);

		if(countriesInfo!=null){
			List<CountryInfo> countryInfoList = countriesInfo.getCountryInfoList();
			if(countryInfoList!=null && !countryInfoList.isEmpty()){
				CountryInfo countryInfo = countryInfoList.get(0);
				ContinentEntity continentEntity = new ContinentEntity(countryInfo.getContinent(), countryInfo.getContinentName());
				List<CountryEntity> countryEntityList = new ArrayList<>();
				continentEntity.setCountryEntityList(countryEntityList);

				CountryEntity countryEntity;
				for(CountryInfo ci:countryInfoList){
					Long geonameId = countryInfo.getGeonameId();

					countryEntity = new CountryEntity(ci.getGeonameId(), ci.getCountryCode(), ci.getCountryName(), ci.getIsoAlpha3(), ci.getCapital(), ci.getPostalCodeFormat());
					countryEntityList.add(countryEntity);
					List<RegionEntity> regionEntityList = new ArrayList<>();
					countryEntity.setRegionEntityList(regionEntityList);

					CountryChildren countryChildren = restTemplate.getForObject(CountryChildren.URL, CountryChildren.class, geonameId, username);
					logger.trace(countryChildren);
					if(countryChildren!=null && countryChildren.getCountryChildrenList()!=null && !countryChildren.getCountryChildrenList().isEmpty()){
						for(CountryChild cch:countryChildren.getCountryChildrenList()){
							regionEntityList.add(new RegionEntity(cch.getGeonameId(), cch.getAdminCode1().getISO3166_2(), cch.getName()));
						}
					}else{
						logger.error("No Children");
						break;
					}
				}
				continentsRepository.save(continentEntity);
			}
		}
	}
}

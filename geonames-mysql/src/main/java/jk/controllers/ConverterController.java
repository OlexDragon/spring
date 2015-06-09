package jk.controllers;

import java.util.ArrayList;
import java.util.List;

import jk.geonames.objects.CountriesInfo;
import jk.geonames.objects.CountryChild;
import jk.geonames.objects.CountryChildren;
import jk.geonames.objects.CountryInfo;
import jk.mysql.entities.address.ContinentEntity;
import jk.mysql.entities.address.CountryEntity;
import jk.mysql.entities.address.RegionEntity;
import jk.mysql.entities.address.RegionTitleEntity;
import jk.mysql.entities.address.repositopies.ContinentsRepository;
import jk.mysql.entities.address.repositopies.CountriesRepository;
import jk.mysql.entities.address.repositopies.RegionTitlesRepository;
import jk.mysql.entities.address.repositopies.RegionsRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConverterController {

	private final Logger logger = LogManager.getLogger();

	@Value("${geonames.username}")
	private String username;

	@Autowired
	private ContinentsRepository continentsRepository;
	@Autowired
	private RegionTitlesRepository regionTitlesRepository;
	@Autowired
	private CountriesRepository countriesRepository;
	@Autowired
	private RegionsRepository regionsRepository;

	@RequestMapping
	public String convertGeonamesToMysql(){
		logger.entry();

		RegionTitleEntity regionTitleEntity = createRegionTitle();

		//Get Country Info
		RestTemplate restTemplate = new RestTemplate();
		CountriesInfo countriesInfo = restTemplate.getForObject(CountriesInfo.URL, CountriesInfo.class, username);
		logger.trace("{}", countriesInfo);

		if(countriesInfo!=null && countriesInfo.getCountryInfoList()!=null && !countriesInfo.getCountryInfoList().isEmpty()){

			List<CountryInfo> countryInfoList = countriesInfo.getCountryInfoList();


				for(CountryInfo ci:countryInfoList){
					logger.trace("{}", ci);

					ContinentEntity continentEntity = createContinent(ci);

					Long geonameId = ci.getGeonameId();
					CountryEntity countryEntity = createCountry(continentEntity, ci);
					addRegionTitle(countryEntity, regionTitleEntity);

					CountryChildren countryChildren = restTemplate.getForObject(CountryChildren.URL, CountryChildren.class, geonameId, username);
					logger.trace(countryChildren);

					if(countryChildren!=null && countryChildren.getCountryChildrenList()!=null && !countryChildren.getCountryChildrenList().isEmpty()){
						for(CountryChild cch:countryChildren.getCountryChildrenList()){
							createRegion(countryEntity, cch);
						}
					}else{
						logger.error("No Children");
					}
				continentsRepository.save(continentEntity);
			}
		}
	
		return "Don";
	}

	private void addRegionTitle(CountryEntity countryEntity, RegionTitleEntity regionTitleEntity) {

		List<RegionTitleEntity> regionTitleEntityList = countryEntity.getRegionTitleEntityList();

		if(regionTitleEntityList == null){
			regionTitleEntityList = new ArrayList<>();
			regionTitleEntityList.add(regionTitleEntity);
			countryEntity.setRegionTitleEntityList(regionTitleEntityList);

		}else if(!regionTitleEntityList.contains(regionTitleEntity))
			regionTitleEntityList.add(regionTitleEntity);
	}

	private RegionTitleEntity createRegionTitle() {
		RegionTitleEntity regionTitleEntity = regionTitlesRepository.findOneByRegionTitle("state");
		if(regionTitleEntity == null)
			regionTitleEntity = regionTitlesRepository.save(new RegionTitleEntity("state"));
		return regionTitleEntity;
	}

	private void createRegion(CountryEntity countryEntity, CountryChild cch) {

		RegionEntity regionEntity = regionsRepository.findOne(cch.getGeonameId());

		if(regionEntity == null){
			List<RegionEntity> regionEntityList = countryEntity.getRegionEntityList();
			regionEntity = new RegionEntity(cch.getGeonameId(), cch.getAdminCode1().getISO3166_2(), cch.getName());

			if(!regionEntityList.contains(regionEntity))
				regionEntityList.add(regionEntity);
		}

		if(regionEntity.getCountryEntity()==null)
			regionEntity.setCountryEntity(countryEntity);
	}

	private CountryEntity createCountry(ContinentEntity continentEntity, CountryInfo ci) {
		CountryEntity countryEntity = countriesRepository.findOne(ci.getGeonameId());

		if(countryEntity == null){
			countryEntity = new CountryEntity(ci.getGeonameId(), ci.getCountryCode(), ci.getCountryName(), ci.getIsoAlpha3(), ci.getCapital(), ci.getPostalCodeFormat());

			List<CountryEntity> countryEntityList = continentEntity.getCountryEntityList();
			if(countryEntityList==null){
				countryEntityList = new ArrayList<>();
				countryEntityList.add(countryEntity);
				continentEntity.setCountryEntityList(countryEntityList);

			}else if(!countryEntityList.contains(countryEntity))
				countryEntityList.add(countryEntity);

		}

		if(countryEntity.getContinentEntity()==null)
			countryEntity.setContinentEntity(continentEntity);

		if(countryEntity.getRegionEntityList()==null){
			List<RegionEntity> regionEntityList = new ArrayList<>();
			countryEntity.setRegionEntityList(regionEntityList);
		}

		return countryEntity;
	}

	private ContinentEntity createContinent(CountryInfo countryInfo) {

		String continentCode = countryInfo.getContinent();
		ContinentEntity continentEntity = continentsRepository.findOne(continentCode);

		if(continentEntity == null){
			continentEntity = new ContinentEntity(continentCode, countryInfo.getContinentName());
		}

		if(continentEntity.getCountryEntityList()==null){
			List<CountryEntity> countryEntityList = new ArrayList<>();
			continentEntity.setCountryEntityList(countryEntityList);
		}

		return continentEntity;
	}
}

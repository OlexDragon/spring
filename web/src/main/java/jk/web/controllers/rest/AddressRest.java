package jk.web.controllers.rest;

import java.util.List;

import jk.web.entities.CountryEntity;
import jk.web.entities.RegionEntity;
import jk.web.entities.repositories.CountryRepository;
import jk.web.entities.repositories.RegionRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class AddressRest {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private CountryRepository countryRepository;
	private List<CountryEntity> countries;
	@RequestMapping(value="/country", produces="application/json;charset=UTF-8")
	public CountryEntity getCountry(@RequestParam Long geonamesId){
		logger.entry(geonamesId);
		return logger.exit(countryRepository.findOne(geonamesId));
	}

	@RequestMapping("/countries")
	public List<CountryEntity> getCountries(){
		logger.entry();
		if(countries==null)
			countries = countryRepository.findAll(new Sort("countryName"));
		return countries;
	}

	@Autowired
	private RegionRepository regionRepository;
	private Object tmp;
	private List<RegionEntity> regions;
	@RequestMapping("regions")
	public List<RegionEntity> getRegions(@RequestParam(required=false) String countryCode, @RequestParam(required=false) Long countryGeonameId){

		if(countryGeonameId!=null){
			if(regions==null || tmp==null || !tmp.equals(countryGeonameId))
				regions = regionRepository.findByCountryEntityGeonamesIdOrderByRegionNameAsc(countryGeonameId);

		}else if(countryCode!=null){
			if(regions==null || tmp==null || !tmp.equals(countryCode))
				regions = regionRepository.findByCountryEntityCountryCodeOrderByRegionNameAsc(countryCode);

		}else
			regions = regionRepository.findByCountryEntityCountryCodeOrderByRegionNameAsc("CA");

		return regions;
	}
}

package jk.web.controllers.rest;


import java.util.List;

import jk.web.entities.CountryEntity;
import jk.web.entities.RegionEntity;
import jk.web.entities.repositories.RegionRepository;
import jk.web.repositories.user.CountryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class AddressRest {

	@Autowired
	private CountryRepository countryRepository;
	private List<CountryEntity> countries;
	@RequestMapping("countries")
	public List<CountryEntity> getCountries(){
		if(countries==null)
			countries = countryRepository.findAll();
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
				regions = regionRepository.findBbyCountryGeonaeId(countryGeonameId, new Sort("region_name"));

		}else if(countryCode!=null){
			if(regions==null || tmp==null || !tmp.equals(countryGeonameId))
				regions = regionRepository.findByCountryCode(countryCode, new Sort("region_name"));

		}else
			regions = regionRepository.findByCountryCode("CA", new Sort("region_name"));

		return regions;
	}
}

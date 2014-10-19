package jk.web.workers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jk.web.html.select.HTMLOptionElement;
import jk.web.user.entities.AddressEntity;
import jk.web.user.entities.CountryEntity;
import jk.web.user.entities.RegionEntity;
import jk.web.user.entities.RegionEntityPK;
import jk.web.user.repository.AddressRepository;
import jk.web.user.repository.CountryRepository;
import jk.web.user.repository.RegionRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

public class AddressWorker {

	private static final Logger logger = LogManager.getLogger();

	public enum AddressStatus{
		ACTIVE,
		OLD
	}

	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private CountryRepository countryRepository;

	private String countryCode;
	private List<HTMLOptionElement> htmlOptionElements;

	public AddressWorker(CountryRepository countryRepository, Set<String> countries) {

		Iterable<CountryEntity> findAll = countryRepository.findAll(new Sort("countryName"));
		if(findAll!=null){
			List<HTMLOptionElement> first = new ArrayList<>();
			List<HTMLOptionElement> rest = new ArrayList<>();
			int legnth = 0;

			for(CountryEntity ce:findAll) {
				String countryName = ce.getCountryName();
				if(legnth<countryName.length())
					legnth = countryName.length();

				HTMLOptionElement option = new HTMLOptionElement().setValue(ce.getCountryCode()).setInnerHTML(countryName);

				if(countries!=null && countries.contains(ce.getCountryCode()))
					first.add(option);
				else
					rest.add(option);
			}

			htmlOptionElements = first;
			htmlOptionElements.add(new HTMLOptionElement().setValue("line").setDisabled(true));
			htmlOptionElements.addAll(rest);
		}
	}

	public List<HTMLOptionElement> getCountries(){
		return htmlOptionElements;
	}

	public CountryEntity getCountryEntity(String countryCode) {
		logger.entry();
		return countryRepository.findOne(countryCode);
	}

	public String getCountryCode() {
		return logger.exit(countryCode);
	}

	public void setCountryCode(String countryCode) {
		logger.entry(countryCode);
		this.countryCode = countryCode==null || countryCode.isEmpty() ? null : countryCode;
	}

	public CountryEntity getCountryEntity() {
		return countryCode!=null ? countryRepository.findOne(countryCode) : null;
	}

	public List<HTMLOptionElement> getRegions(){
		List<HTMLOptionElement> regions = new ArrayList<HTMLOptionElement>();
		if(countryCode!=null){
			List<RegionEntity> findByCountryCode = regionRepository.findByCountryCode(countryCode);
			if(findByCountryCode!=null)
				for(RegionEntity r:findByCountryCode)
					regions.add(new HTMLOptionElement().setValue(r.getRegionEntityPK().getRegionCode()).setInnerHTML(r.getRegionName()));
		}
		return regions;
	}

	public RegionEntity getRegionEntity(String regionCode) {
		logger.entry(regionCode);
		RegionEntity regionEntity = null;

		if(regionCode!=null && countryCode!=null)
			regionEntity = regionRepository.findOne(new RegionEntityPK(regionCode, countryCode));

		return regionEntity;
	}

	public AddressEntity save(AddressEntity addressEntity) {
		logger.entry(addressEntity);
		List<AddressEntity> addresses = addressRepository.findByUserId(addressEntity.getUserId());
		AddressEntity savedEntity = null;
		if(addresses!=null){
			savedEntity = getFrom(addresses, addressEntity);
			logger.trace("\n\t{}", savedEntity);
			if(savedEntity==null){
				for(AddressEntity ae:addresses)
					if(ae.getStatus()==AddressStatus.ACTIVE)
						addressRepository.save(ae.setStatus(AddressStatus.OLD).setStatusUpdateDate(new Date()));
				savedEntity = addressRepository.save(addressEntity);
			}
		}else
			savedEntity = addressRepository.save(addressEntity);

		return savedEntity;
	}

	public String getRegionName() {
		CountryEntity countryEntity = getCountryEntity();
		logger.trace("\n\t{}", countryEntity);
		return countryEntity!=null ? countryEntity.getRegionName() : null;
	}

	public static AddressEntity getFrom(List<AddressEntity> addressEntities, AddressEntity addressEntity) {
		logger.entry(addressEntity, addressEntities);
		AddressEntity containsAE = null;
		if(addressEntities!=null && addressEntity!=null)
			for(AddressEntity ae:addressEntities){

				//Address
				String address = ae.getAddress();
				String newAddress = addressEntity.getAddress();
				if(address!=null ? !address.equals(newAddress) : newAddress!=null)
					continue;//if not equal check next entity
				

				//City
				String city = ae.getCity();
				String newCity = addressEntity.getCity();
				if(city!=null ? !city.equals(newCity) : newCity!=null)
					continue;//if not equal check next

				//Country
				String countryCode = ae.getCountryCode();
				String newCountryCode = addressEntity.getCountryCode();
				if(countryCode!=null ? countryCode.equals(newCountryCode) : newCountryCode!=null)
					continue;//if not equal check next entity

				//Postal Code
				String postalCode = ae.getPostalCode();
				String newPostalCode = addressEntity.getPostalCode();
				if(postalCode!=null ? postalCode.equals(newPostalCode) : newPostalCode!=null)
					continue;//if not equal check next entity

				//Region
				String regionsCode = ae.getRegionsCode();
				String newRegionsCode = addressEntity.getRegionsCode();
				if(regionsCode!=null ? regionsCode.equals(newRegionsCode) : newRegionsCode!=null)
					continue;//if not equal check next entity

				containsAE = ae;
				break;
			}
		return containsAE;
		
	}
}

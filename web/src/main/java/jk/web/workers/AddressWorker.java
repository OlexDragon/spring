package jk.web.workers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jk.web.entities.AddressEntity;
import jk.web.entities.CountryEntity;
import jk.web.entities.RegionEntity;
import jk.web.entities.repositories.AddressRepository;
import jk.web.entities.repositories.CountryRepository;
import jk.web.entities.repositories.RegionRepository;
import jk.web.html.select.HTMLOptionElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

public class AddressWorker {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private CountryRepository countryRepository;
	private List<CountryEntity> allCountries;

	private static List<HTMLOptionElement> htmlOptionElements;

	public AddressWorker(CountryRepository countryRepository, Set<String> countries) {

		allCountries = countryRepository.findAll(new Sort("countryName"));
		if(allCountries!=null){
			List<HTMLOptionElement> first = new ArrayList<>();
			List<HTMLOptionElement> rest = new ArrayList<>();
			int legnth = 0;

			for(CountryEntity ce:allCountries) {
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

	public static List<HTMLOptionElement> getCountries(){
		return htmlOptionElements;
	}

	public CountryEntity getCountryEntity(String countryCode) {
		return null;//countryCode!=null ? countryRepository.findOne(countryCode) : null;
	}

	public List<RegionEntity> getRegionEntities(String countryCode){
		List<RegionEntity> regions = null;
		if(countryCode!=null){
//			regions = regionRepository.findByCountryCode(countryCode);
		}else
			regions = null;
		return regions;
	}

	public RegionEntity getRegionEntity(String countryCode, String regionCode) {
		logger.entry(regionCode);
		RegionEntity regionEntity = null;

//		if(regionCode!=null && countryCode!=null)
//			regionEntity = regionRepository.findOne(new RegionEntityPK(regionCode, countryCode));

		return regionEntity;
	}

	public AddressEntity save(AddressEntity addressEntity) {
		logger.entry(addressEntity);
//		List<AddressEntity> addresses = addressRepository.findByUserIdAndType(addressEntity.getUserId(), addressEntity.getType());
//		AddressEntity savedEntity = null;
//		if(addresses!=null){
//			savedEntity = getFrom(addresses, addressEntity);
//			logger.trace("\n\t{}", savedEntity);
//			if(savedEntity==null){
//				for(AddressEntity ae:addresses)
//					if(ae.getStatus()==AddressStatus.ACTIVE)
//						addressRepository.save(ae.setStatus(AddressStatus.OLD).setStatusUpdateDate(new Date()));
//				savedEntity = addressRepository.save(addressEntity);
//			}
//		}else
//			savedEntity = addressRepository.save(addressEntity);

		return null;//savedEntity;
	}

	public String getRegionName(String countryCode) {
		CountryEntity countryEntity = getCountryEntity(countryCode);
		logger.trace("\n\t{}", countryEntity);
		return null;//countryEntity!=null ? countryEntity.getRegionName() : null;
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
/*				String regionsCode = ae.getRegionsCode();
				String newRegionsCode = addressEntity.getRegionsCode();
				if(regionsCode!=null ? regionsCode.equals(newRegionsCode) : newRegionsCode!=null)
					continue;//if not equal check next entity
*/
				containsAE = ae;
				break;
			}
		return containsAE;
		
	}
//
//	public List<AddressEntity> getAddressEntities(Long userId, AddressType addressType) {
//		return addressRepository.findByUserIdAndType(userId, addressType);
//	}

	public List<CountryEntity> getAllCountries() {
		return allCountries;
	}
}

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

	private final Logger logger = LogManager.getLogger();

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
			htmlOptionElements.add(new HTMLOptionElement().setInnerHTML(getSeparator(legnth/2)).setValue("disabled").setDisabled(true));
			htmlOptionElements.addAll(rest);
		}
	}

	private String getSeparator(int legnth) {
		final char separatorChar = '─';
		char[] buffer = new char[legnth];
		Arrays.fill(buffer, separatorChar);
		return new String(buffer);
	}

	public List<HTMLOptionElement> getCountries(){
		return htmlOptionElements;
	}

	public CountryEntity getCountryEntity(String countryCode) {
		return countryRepository.findOne(countryCode);
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode==null || countryCode.isEmpty() || countryCode.equals("Select") ? null : countryCode;
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
		if(addresses!=null)
			for(AddressEntity ae:addresses)
				if(ae.getStatus()==AddressStatus.ACTIVE)
					addressRepository.save(ae.setStatus(AddressStatus.OLD).setStatusUpdateDate(new Date()));

		return addressRepository.save(addressEntity);
	}

	public String getRegionName() {
		CountryEntity countryEntity = getCountryEntity();
		return countryEntity!=null ? countryEntity.getRegionName() : null;
	}
}

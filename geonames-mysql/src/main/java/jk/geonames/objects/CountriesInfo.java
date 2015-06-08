package jk.geonames.objects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "geonames")
public class CountriesInfo {

	public static final String URL = "http://api.geonames.org/countryInfo?username={username}";

	private List<CountryInfo> countryInfoList;

	@XmlElement(name="country")
	public List<CountryInfo> getCountryInfoList() {
		return countryInfoList;
	}

	public void setCountryInfoList(List<CountryInfo> countryInfoList) {
		this.countryInfoList = countryInfoList;
	}

	@Override
	public String toString() {
		return "\n\tCountriesInfo [countryInfoList=" + countryInfoList + "]";
	}
}

package jk.geonames.objects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "geonames")
public class CountryChildren {

	public static final String URL = "http://api.geonames.org/children?geonameId={geonameId}&style=FULL&username={username}";

	@XmlElement
	private Integer totalResultsCount;
	@XmlElement(name="geoname")
	private List<CountryChild> countryChildrenList;

	public Integer getTotalResultsCount() {
		return totalResultsCount;
	}
	public void setTotalResultsCount(Integer totalResultsCount) {
		this.totalResultsCount = totalResultsCount;
	}
	public List<CountryChild> getCountryChildrenList() {
		return countryChildrenList;
	}
	public void setCountryChildrenList(List<CountryChild> countryChildrenList) {
		this.countryChildrenList = countryChildrenList;
	}
	@Override
	public String toString() {
		return "\n\tCountryChildren [totalResultsCount=" + totalResultsCount
				+ ", countryChildrenList=" + countryChildrenList + "]";
	}
}

package jk.geonames.objects;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="country")
public class CountryInfo {
	private String countryCode;
	private String countryName;
	private Integer isoNumeric;
	private String isoAlpha3;
	private String fipsCode;
	private String continent;
	private String continentName;
	private String capital;
	private BigDecimal areaInSqKm;
	private Integer population;
	private String currencyCode;
	private String languages;
	private Long geonameId;
	private BigDecimal west;
	private BigDecimal north;
	private BigDecimal east;
	private BigDecimal south;
	private String postalCodeFormat;

	@XmlElement
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	@XmlElement
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	@XmlElement
	public Integer getIsoNumeric() {
		return isoNumeric;
	}
	public void setIsoNumeric(Integer isoNumber) {
		this.isoNumeric = isoNumber;
	}
	@XmlElement
	public String getIsoAlpha3() {
		return isoAlpha3;
	}
	public void setIsoAlpha3(String isoAlpha3) {
		this.isoAlpha3 = isoAlpha3;
	}
	@XmlElement
	public String getFipsCode() {
		return fipsCode;
	}
	public void setFipsCode(String fipsCode) {
		this.fipsCode = fipsCode;
	}
	@XmlElement
	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
	@XmlElement
	public String getContinentName() {
		return continentName;
	}
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}
	@XmlElement
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	@XmlElement
	public BigDecimal getAreaInSqKm() {
		return areaInSqKm;
	}
	public void setAreaInSqKm(BigDecimal areaInSqKm) {
		this.areaInSqKm = areaInSqKm;
	}
	@XmlElement
	public Integer getPopulation() {
		return population;
	}
	public void setPopulation(Integer population) {
		this.population = population;
	}
	@XmlElement
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	@XmlElement
	public String getLanguages() {
		return languages;
	}
	public void setLanguages(String languages) {
		this.languages = languages;
	}
	@XmlElement
	public Long getGeonameId() {
		return geonameId;
	}
	public void setGeonameId(Long geonameId) {
		this.geonameId = geonameId;
	}
	@XmlElement
	public BigDecimal getWest() {
		return west;
	}
	public void setWest(BigDecimal west) {
		this.west = west;
	}
	@XmlElement
	public BigDecimal getNorth() {
		return north;
	}
	public void setNorth(BigDecimal north) {
		this.north = north;
	}
	@XmlElement
	public BigDecimal getEast() {
		return east;
	}
	public void setEast(BigDecimal east) {
		this.east = east;
	}
	@XmlElement
	public BigDecimal getSouth() {
		return south;
	}
	public void setSouth(BigDecimal south) {
		this.south = south;
	}
	@XmlElement
	public String getPostalCodeFormat() {
		return postalCodeFormat;
	}
	public void setPostalCodeFormat(String postalCodeFormat) {
		this.postalCodeFormat = postalCodeFormat;
	}
	@Override
	public String toString() {
		return "\n\tCountryInfo [countryCode=" + countryCode + ", countruName=" + countryName + ", isoNumeric=" + isoNumeric + ", isoAlpha3=" + isoAlpha3 + ", fipsCode=" + fipsCode
				+ ", continent=" + continent + ", continentName=" + continentName + ", capital=" + capital + ", areaInSqKm=" + areaInSqKm + ", population=" + population
				+ ", currencyCode=" + currencyCode + ", languages=" + languages + ", geonameId=" + geonameId + ", west=" + west + ", north=" + north + ", east=" + east
				+ ", south=" + south + ", postalCodeFormate=" + postalCodeFormat + "]";
	}
}

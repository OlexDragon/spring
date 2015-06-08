package jk.geonames.objects;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="geoname")
public class CountryChild {

	@XmlElement
	private String toponymName;
	@XmlElement(name="adminCode1")
	private AdminCode1 adminCode1;
	@XmlElement
	private String name;
	@XmlElement
	private BigDecimal lat;
	@XmlElement
	private BigDecimal lng;
	@XmlElement
	private Long geonameId;
	@XmlElement
	private String countryCode;
	@XmlElement
	private String countryName;
	@XmlElement
	private String fcl;
	@XmlElement
	private String fcode;
	@XmlElement
	private String fclName;
	@XmlElement
	private String fcodeName;
	@XmlElement
	private Integer population;
	@XmlElement
	private String asciiName;

	public String getToponymName() {
		return toponymName;
	}
	public void setToponymName(String toponymName) {
		this.toponymName = toponymName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getLat() {
		return lat;
	}
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}
	public BigDecimal getLng() {
		return lng;
	}
	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}
	public Long getGeonameId() {
		return geonameId;
	}
	public void setGeonameId(Long geonameId) {
		this.geonameId = geonameId;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getFcl() {
		return fcl;
	}
	public void setFcl(String fcl) {
		this.fcl = fcl;
	}
	public String getFcode() {
		return fcode;
	}
	public void setFcode(String fcode) {
		this.fcode = fcode;
	}
	public String getFclName() {
		return fclName;
	}
	public void setFclName(String fclName) {
		this.fclName = fclName;
	}
	public String getFcodeName() {
		return fcodeName;
	}
	public void setFcodeName(String fcodeName) {
		this.fcodeName = fcodeName;
	}
	public Integer getPopulation() {
		return population;
	}
	public void setPopulation(Integer population) {
		this.population = population;
	}
	public String getAsciiName() {
		return asciiName;
	}
	public void setAsciiName(String asciiName) {
		this.asciiName = asciiName;
	}
	public AdminCode1 getAdminCode1() {
		return adminCode1;
	}
	public void setAdminCode1(AdminCode1 iSO3166_2) {
		adminCode1 = iSO3166_2;
	}
	@Override
	public String toString() {
		return "\n\tCountryChild [toponymName=" + toponymName + ", ISO3166_2="
				+ adminCode1 + ", name=" + name + ", lat=" + lat + ", lng="
				+ lng + ", geonameId=" + geonameId + ", countryCode="
				+ countryCode + ", countryName=" + countryName + ", fcl=" + fcl
				+ ", fcode=" + fcode + ", fclName=" + fclName + ", fcodeName="
				+ fcodeName + ", population=" + population + ", asciiName="
				+ asciiName + "]";
	}
}

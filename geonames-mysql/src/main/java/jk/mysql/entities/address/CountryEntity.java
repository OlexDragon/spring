/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.mysql.entities.address;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "countries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CountryEntity.findAll", query = "SELECT c FROM CountryEntity c")})
public class CountryEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "geonames_id")
    private Long geonamesId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "country_code")
    private String countryCode;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 52)
    @Column(name = "country_name")
    private String countryName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "iso_alfa_3")
    private String isoAlfa3;

    @Basic(optional = true)
    @Size(max = 145)
    @Column(name = "capital")
    private String capital;

    @Basic(optional = true)
    @Size(max = 145)
    @Column(name = "postal_code_format")
    private String postalCodeFormat;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryEntity", fetch=FetchType.EAGER)
    @OrderColumn(name="order_column")
    private List<RegionEntity> regionEntityList;

    @JoinTable(name = "region_titles_has_countries", joinColumns = {
    		@JoinColumn(name = "countries_geonames_id", referencedColumnName = "geonames_id")},
    	inverseJoinColumns = {
            @JoinColumn(name = "region_titles_region_title_id", referencedColumnName = "region_title_id")})
    @ManyToMany(fetch=FetchType.EAGER)
    private List<RegionTitleEntity> regionTitleEntityLisr;

    @JoinColumn(name = "continent_code", referencedColumnName = "continent_code", updatable=false)
    @ManyToOne(optional = false)
    private ContinentEntity continentEntity;

    public CountryEntity() {
    }

    public CountryEntity(Long geonamesId) {
        this.geonamesId = geonamesId;
    }

    public CountryEntity(Long geonamesId, String countryCode, String countryName, String isoAlfa3, String capital, String postalCodeFormat) {
        this.geonamesId = geonamesId;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.isoAlfa3 = isoAlfa3;
        this.capital = capital;
        this.postalCodeFormat = postalCodeFormat;
    }

    public Long getGeonamesId() {
        return geonamesId;
    }

    public void setGeonamesId(Long geonamesId) {
        this.geonamesId = geonamesId;
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

    public String getIsoAlfa3() {
        return isoAlfa3;
    }

    public void setIsoAlfa3(String isoAlfa3) {
        this.isoAlfa3 = isoAlfa3;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getPostalCodeFormat() {
        return postalCodeFormat;
    }

    public void setPostalCodeFormat(String postalCodeFormat) {
        this.postalCodeFormat = postalCodeFormat;
    }

    @XmlTransient
    public List<RegionEntity> getRegionEntityList() {
        return regionEntityList;
    }

    public void setRegionEntityList(List<RegionEntity> regionEntityList) {
        this.regionEntityList = regionEntityList;
    }

    @XmlTransient
    public List<RegionTitleEntity> getRegionTitleEntityList() {
        return regionTitleEntityLisr;
    }

    public void setRegionTitleEntityList(List<RegionTitleEntity> regionTitleEntityList) {
        this.regionTitleEntityLisr = regionTitleEntityList;
    }

    public ContinentEntity getContinentEntity() {
        return continentEntity;
    }

    public void setContinentEntity(ContinentEntity continentEntity) {
        this.continentEntity = continentEntity;
    }

    @Override
    public int hashCode() {
        return geonamesId != null ? geonamesId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof CountryEntity ? object.hashCode() == hashCode() : false;
    }

    @Override
	public String toString() {
		return "\n\tCountryEntity [geonamesId=" + geonamesId + ", countryCode="
				+ countryCode + ", countryName=" + countryName + ", isoAlfa3="
				+ isoAlfa3 + ", capital=" + capital + ", postalCodeFormat="
				+ postalCodeFormat + ", regionEntityList=" + regionEntityList
				+ ", regionTitleEntityLisr=" + regionTitleEntityLisr + "]";
	}    
}

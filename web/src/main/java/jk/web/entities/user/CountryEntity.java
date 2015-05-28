/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.entities.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "countries", catalog = "jk", schema = "")
@XmlRootElement
public class CountryEntity implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryCode")
    private List<AddressEntity> addressEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryEntity")
    private List<RegionEntity> regionEntityList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "country_code")
    private String countryCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 52)
    @Column(name = "country_name")
    private String countryName;
    @Size(max = 145)
    @Column(name = "region_name")
    private String regionName;

    public CountryEntity() {
    }

    public CountryEntity(String countryCode) {
        this.countryCode = countryCode;
    }

    public CountryEntity(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public int hashCode() {
        return countryCode != null ? countryCode.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj!=null ? obj.hashCode()==hashCode() : false;
    }

	@Override
	public String toString() {
		return "CountryEntity [countryCode=" + countryCode + ", countryName=" + countryName + ", regionName=" + regionName + "]";
	}

    public List<AddressEntity> getAddressEntityList() {
        return addressEntityList;
    }

    public void setAddressEntityList(List<AddressEntity> addressEntityList) {
        this.addressEntityList = addressEntityList;
    }

    public List<RegionEntity> getRegionEntityList() {
        return regionEntityList;
    }

    public void setRegionEntityList(List<RegionEntity> regionEntityList) {
        this.regionEntityList = regionEntityList;
    }

}

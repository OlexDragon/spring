/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jk.web.entities.user.CountryEntity;
import jk.web.user.Address.AddressStatus;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "addresses", catalog = "jk", schema = "")
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "addsress_id")
    private Long addsressId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 245)
    @Column(name = "addsress")
    private String address;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "city")
    private String city;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "postal_code")
    private String postalCode;

    @Basic(optional=true)
    @Column(name = "regions_code")
    private String regionsCode;

    @JoinColumns({
        @JoinColumn(name = "regions_code", referencedColumnName = "region_code", insertable=false, updatable=false),
        @JoinColumn(name = "country_code", referencedColumnName = "country_code", insertable=false, updatable=false)})
    @ManyToOne(fetch=FetchType.EAGER, optional=true)
    private RegionEntity regionEntity;

    @Basic
    @Column(name = "country_code")
    private String countryCode;

    @JoinColumn(name = "country_code", referencedColumnName = "country_code", insertable=false, updatable=false)
    @ManyToOne(fetch=FetchType.EAGER, optional=true)
    private CountryEntity countryEntity;

    @ManyToMany(mappedBy = "addressEntityList")
    private List<BusinessEntity> businessEntityList;

    @Column(name = "address_status")
    @Enumerated(EnumType.ORDINAL)
    private AddressStatus status = AddressStatus.ACTIVE;

   @Column(name = "create_date", insertable=false, updatable=false)
    private Timestamp createDate;

    public AddressEntity() {
    }

    public AddressEntity(Long addsressId) {
        this.addsressId = addsressId;
    }

    public AddressEntity(Long addsressId, String addsress, String city, String postalCode, Timestamp createDate) {
        this.addsressId = addsressId;
        this.address = addsress;
        this.city = city;
        this.postalCode = postalCode;
        this.createDate = createDate;
    }

    public Long getAddsressId() {
        return addsressId;
    }

    public void setAddsressId(Long addsressId) {
        this.addsressId = addsressId;
    }

	public String getAddress() {
        return address;
    }

    public AddressEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public AddressEntity setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public RegionEntity getRegionEntity() {
        return regionEntity;
    }

    public AddressEntity setRegionEntity(RegionEntity regionEntity) {
        this.regionEntity = regionEntity;
        return this;
    }

    public CountryEntity getCountryEntity() {
        return countryEntity;
    }

    public AddressEntity setCountryEntity(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
        return this;
    }

    public String getRegionsCode() {
		return regionsCode;
	}

	public AddressEntity setRegionsCode(String regionsCode) {
		this.regionsCode = regionsCode;
		return this;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public AddressEntity setCountryCode(String countryCode) {
		this.countryCode = countryCode;
		return this;
	}

    public AddressStatus getStatus() {
        return status;
    }

    public void setStatus(AddressStatus status) {
        this.status = status;
    }

    public List<BusinessEntity> getBusinessEntityList() {
        return businessEntityList;
    }

    public void setBusinessEntityList(List<BusinessEntity> businessEntityList) {
        this.businessEntityList = businessEntityList;
    }

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (addsressId != null ? addsressId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AddressEntity)) {
            return false;
        }
        AddressEntity other = (AddressEntity) object;
        if ((this.addsressId == null && other.addsressId != null) || (this.addsressId != null && !this.addsressId.equals(other.addsressId))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "AddressEntity [addsressId=" + addsressId + ", address=" + address + ", city=" + city + ", postalCode=" + postalCode + ", status=" + status
				+ ", createDate=" + createDate + ", regionEntity=" + regionEntity + ", countryEntity=" + countryEntity + "]";
	}
}
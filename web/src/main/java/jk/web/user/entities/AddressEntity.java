/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.user.entities;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import jk.web.user.Address.AddressStatus;
import jk.web.user.Address.AddressType;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "addresses", catalog = "jk", schema = "")
@XmlRootElement
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "addsress_id")
    private Long addsressId;

    @Basic(optional = false)
    @Column(name = "logins_loginID")
    private Long userId;

    @Basic
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private AddressType type;

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

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private AddressStatus status = AddressStatus.ACTIVE;

    @Column(name = "status_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusUpdateDate;

    @Basic
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Basic
    @Column(name = "regions_code")
    private String regionsCode;
    @JoinColumns({
        @JoinColumn(name = "regions_code", referencedColumnName = "region_code", insertable=false, updatable=false),
        @JoinColumn(name = "country_code", referencedColumnName = "country_code", insertable=false, updatable=false)})
    @ManyToOne(fetch=FetchType.EAGER)
    private RegionEntity regionEntity;

    @Basic
    @Column(name = "country_code")
    private String countryCode;
    @JoinColumn(name = "country_code", referencedColumnName = "country_code", insertable=false, updatable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private CountryEntity countryEntity;

    public AddressEntity() {
    }

    public AddressEntity(Long addsressId) {
        this.addsressId = addsressId;
    }

    public AddressEntity(Long addsressId, String addsress, String city, String postalCode, Date createDate) {
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

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public AddressType getType() {
		return type;
	}

	public AddressEntity setType(AddressType type) {
		this.type = type;
		return this;
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

    public AddressStatus getStatus() {
        return status;
    }

    public AddressEntity setStatus(AddressStatus status) {
        this.status = status;
        return this;
    }

    public Date getStatusUpdateDate() {
        return statusUpdateDate;
    }

    public AddressEntity setStatusUpdateDate(Date statusUpdateDate) {
        this.statusUpdateDate = statusUpdateDate;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
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
		return "AddressEntity [addsressId=" + addsressId + ", userId=" + userId + ", address=" + address + ", city=" + city + ", postalCode=" + postalCode + ", status=" + status
				+ ", statusUpdateDate=" + statusUpdateDate + ", createDate=" + createDate + ", regionEntity=" + regionEntity + ", countryEntity=" + countryEntity + "]";
	}
}

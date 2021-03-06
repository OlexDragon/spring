package jk.web.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "addresses", catalog = "", schema = "jk")
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	public enum AddressStatus{
		ACTIVE,
		OLD
	}

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
    @Column(name = "region")
    private String region;

    @Basic
    @Column(name = "country_code")
    private String countryCode;

    @JoinColumn(name = "country_code", referencedColumnName = "country_code", insertable=false, updatable=false)
    @ManyToOne(fetch=FetchType.EAGER, optional=true)
    private CountryEntity countryEntity;

   @Column(name = "create_date", insertable=false, updatable=false)
    private Timestamp createDate;

    public AddressEntity() {
    }

    public AddressEntity(Long addsressId) {
        this.addsressId = addsressId;
    }

    public AddressEntity(String address, String city, String region, String countryCode, String postalCode) {
        this.address = address;
        this.city = city;
        this.region = region;
        this.countryCode = countryCode;
        this.postalCode = postalCode;
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

    public CountryEntity getCountryEntity() {
        return countryEntity;
    }

    public AddressEntity setCountryEntity(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
        return this;
    }

    public String getRegion() {
		return region;
	}

	public AddressEntity setRegionsCode(String region) {
		this.region = region;
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
        return addsressId != null ? addsressId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
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
		return "AddressEntity [addsressId=" + addsressId + ", address=" + address + ", city=" + city + ", postalCode=" + postalCode
				+ ", createDate=" + createDate + ", countryEntity=" + countryEntity + "]";
	}
}

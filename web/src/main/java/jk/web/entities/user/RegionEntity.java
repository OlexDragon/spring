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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Oleksandr Potomkin
 */
@Entity(name="region")
@Table(name = "regions", catalog = "jk", schema = "")
@XmlRootElement
public class RegionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected RegionEntityPK regionEntityPK;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "region_name")
    private String regionName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regionEntity")
    private List<AddressEntity> addressEntityList;

    @JoinColumn(name = "country_code", referencedColumnName = "country_code", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CountryEntity countryEntity;

    public RegionEntity() {
    }

    public RegionEntity(RegionEntityPK regionEntityPK) {
        this.regionEntityPK = regionEntityPK;
    }

    public RegionEntity(RegionEntityPK regionEntityPK, String regionName) {
        this.regionEntityPK = regionEntityPK;
        this.regionName = regionName;
    }

    public RegionEntity(String regionCode, String countryCode) {
        this.regionEntityPK = new RegionEntityPK(regionCode, countryCode);
    }

    public RegionEntityPK getRegionEntityPK() {
        return regionEntityPK;
    }

    public void setRegionEntityPK(RegionEntityPK regionEntityPK) {
        this.regionEntityPK = regionEntityPK;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @XmlTransient
    public List<AddressEntity> getAddressEntityList() {
        return addressEntityList;
    }

    public void setAddressEntityList(List<AddressEntity> addressEntityList) {
        this.addressEntityList = addressEntityList;
    }

    public CountryEntity getCountryEntity() {
        return countryEntity;
    }

    public void setCountryEntity(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regionEntityPK != null ? regionEntityPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegionEntity)) {
            return false;
        }
        RegionEntity other = (RegionEntity) object;
        if ((this.regionEntityPK == null && other.regionEntityPK != null) || (this.regionEntityPK != null && !this.regionEntityPK.equals(other.regionEntityPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.user.entities.RegionEntity[ regionEntityPK=" + regionEntityPK + " ]";
    }

}

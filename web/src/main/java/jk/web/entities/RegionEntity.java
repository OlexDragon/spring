/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "regions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegionEntity.findAll", query = "SELECT r FROM RegionEntity r")})
public class RegionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "geonames_id")
    private Integer geonamesId;

    @Size(max = 5)
    @Column(name = "region_code")
    private String regionCode;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "region_name")
    private String regionName;

    @JsonIgnore
    @Column(name = "order_column")
    private Integer orderColumn;

    @JoinColumn(name = "country_code", referencedColumnName = "country_code")
    @ManyToOne(optional = false)
    private CountryEntity countryEntity;

    public RegionEntity() {
    }

    public RegionEntity(Integer geonamesId) {
        this.geonamesId = geonamesId;
    }

    public RegionEntity(Integer geonamesId, String regionName) {
        this.geonamesId = geonamesId;
        this.regionName = regionName;
    }

    public Integer getGeonamesId() {
        return geonamesId;
    }

    public void setGeonamesId(Integer geonamesId) {
        this.geonamesId = geonamesId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(Integer orderColumn) {
        this.orderColumn = orderColumn;
    }

    public CountryEntity getCountryCode() {
        return countryEntity;
    }

    public void setCountryCode(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geonamesId != null ? geonamesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegionEntity)) {
            return false;
        }
        RegionEntity other = (RegionEntity) object;
        if ((this.geonamesId == null && other.geonamesId != null) || (this.geonamesId != null && !this.geonamesId.equals(other.geonamesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.RegionEntity[ geonamesId=" + geonamesId + " ]";
    }
    
}

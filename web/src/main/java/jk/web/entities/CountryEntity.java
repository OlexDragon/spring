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

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "capital")
    private String capital;

    @Basic(optional = false)
    @NotNull
    @Column(name = "geoname_id")
    private Long geonameId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "postal_code_format")
    private String postalCodeFormat;

    @JoinColumn(name = "continent_id", referencedColumnName = "continent_id")
    @ManyToOne(optional = false)
    private ContinentEntity continents;

    public CountryEntity() {
    }

    public CountryEntity(String countryCode) {
        this.countryCode = countryCode;
    }

    public CountryEntity(String countryCode, String countryName, String isoAlfa3, String capital, Long geonameId, String postalCodeFormat) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.isoAlfa3 = isoAlfa3;
        this.capital = capital;
        this.geonameId = geonameId;
        this.postalCodeFormat = postalCodeFormat;
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

    public Long getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Long geonameId) {
        this.geonameId = geonameId;
    }

    public String getPostalCodeFormat() {
        return postalCodeFormat;
    }

    public void setPostalCodeFormat(String postalCodeFormat) {
        this.postalCodeFormat = postalCodeFormat;
    }

    public ContinentEntity getContinents() {
        return continents;
    }

    public void setContinents(ContinentEntity continents) {
        this.continents = continents;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countryCode != null ? countryCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CountryEntity)) {
            return false;
        }
        CountryEntity other = (CountryEntity) object;
        if ((this.countryCode == null && other.countryCode != null) || (this.countryCode != null && !this.countryCode.equals(other.countryCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.CountryEntity[ countryCode=" + countryCode + " ]";
    }
    
}

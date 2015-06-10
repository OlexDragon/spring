/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "continents")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContinentEntity.findAll", query = "SELECT c FROM ContinentEntity c")})
public class ContinentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "continent_code")
    private String continentCode;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "continent_name")
    private String continentName;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "continentCode")
    private List<CountryEntity> countryEntityList;

    public ContinentEntity() {
    }

    public ContinentEntity(String continentCode) {
        this.continentCode = continentCode;
    }

    public ContinentEntity(String continentCode, String continentName) {
        this.continentCode = continentCode;
        this.continentName = continentName;
    }

    public String getContinentCode() {
        return continentCode;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    @XmlTransient
    public List<CountryEntity> getCountryEntityList() {
        return countryEntityList;
    }

    public void setCountryEntityList(List<CountryEntity> countryEntityList) {
        this.countryEntityList = countryEntityList;
    }

    @Override
    public int hashCode() {
        return continentCode != null ? continentCode.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ContinentEntity ? object.hashCode() == hashCode() : false;
    }

    @Override
    public String toString() {
        return "jk.web.entities.ContinentEntity[ continentCode=" + continentCode + " ]";
    }
    
}

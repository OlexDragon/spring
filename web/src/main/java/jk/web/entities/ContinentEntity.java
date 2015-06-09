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
public class ContinentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "continent_id")
    private String continentId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "continent_name")
    private String continentName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ContinentEvtities")
    private List<CountryEntity> countryEntityList;

    public ContinentEntity() {
    }

    public ContinentEntity(String continentId) {
        this.continentId = continentId;
    }

    public ContinentEntity(String continentId, String continentName) {
        this.continentId = continentId;
        this.continentName = continentName;
    }

    public String getContinentId() {
        return continentId;
    }

    public void setContinentId(String continentId) {
        this.continentId = continentId;
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
        int hash = 0;
        hash += (continentId != null ? continentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContinentEntity)) {
            return false;
        }
        ContinentEntity other = (ContinentEntity) object;
        if ((this.continentId == null && other.continentId != null) || (this.continentId != null && !this.continentId.equals(other.continentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.Continents[ continentId=" + continentId + " ]";
    }
    
}

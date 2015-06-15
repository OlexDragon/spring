package jk.web.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "continents", catalog = "", schema = "jk")
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "continentEntity", fetch=FetchType.LAZY)
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
        int hash = 0;
        hash += (continentCode != null ? continentCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContinentEntity)) {
            return false;
        }
        ContinentEntity other = (ContinentEntity) object;
        if ((this.continentCode == null && other.continentCode != null) || (this.continentCode != null && !this.continentCode.equals(other.continentCode))) {
            return false;
        }
        return true;
    }

    @Override
	public String toString() {
		return "\n\tContinentEntity [continentCode=" + continentCode + ", continentName=" + continentName + ", countryEntityList=" + countryEntityList + "]";
	}
    
}

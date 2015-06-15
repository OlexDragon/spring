package jk.web.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "region_titles", catalog = "", schema = "jk")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegionTitleEntity.findAll", query = "SELECT r FROM RegionTitleEntity r")})
public class RegionTitleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "region_title_id")
    private Long regionTitleId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "region_title")
    private String regionTitle;

    @JsonIgnore
    @ManyToMany(mappedBy = "regionTitleEntityList")
    private List<CountryEntity> countryEntityList;

    public RegionTitleEntity() {
    }

    public RegionTitleEntity(Long regionTitleId) {
        this.regionTitleId = regionTitleId;
    }

    public RegionTitleEntity(String regionTitle) {
        this.regionTitle = regionTitle;
    }

    public Long getRegionTitleId() {
        return regionTitleId;
    }

    public void setRegionTitleId(Long regionTitleId) {
        this.regionTitleId = regionTitleId;
    }

    public String getRegionTitle() {
        return regionTitle;
    }

    public void setRegionTitle(String regionTitle) {
        this.regionTitle = regionTitle;
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
        return regionTitleId != null ? regionTitleId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof RegionTitleEntity ? object.hashCode() == hashCode() : false;
    }

    @Override
	public String toString() {
		return "\n\tRegionTitleEntity [regionTitleId=" + regionTitleId + ", regionTitle=" + regionTitle + "]";
	}    
}

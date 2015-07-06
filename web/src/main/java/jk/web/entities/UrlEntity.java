/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jk.web.entities.business.BusinessEntity;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "urls", catalog = "jk", schema = "")
@NamedQueries({
    @NamedQuery(name = "UrlEntity.findAll", query = "SELECT u FROM UrlEntity u")})
public class UrlEntity implements Serializable {
	private static final long serialVersionUID = -6239926424978624933L;

    enum UrlStatus{
    	UNKNOWN,
    	ACTIVE,
    	INACTIVE
    }

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "url_id")
    private Long urlId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "url")
    private String url;

    @ManyToMany(mappedBy = "urlEntityList")
    private List<BusinessEntity> businessEntityList;

    @JoinColumn(name = "url_country_of_activity", referencedColumnName = "geonames_id", nullable = false, insertable=false, updatable=false)
    @ManyToOne(optional = false)
    private CountryEntity urlCountryOfActivity;

    public UrlEntity() {
    }

    public UrlEntity(Long urlId) {
        this.urlId = urlId;
    }

    public UrlEntity(String url) {
        this.url = url;
    }

    public Long getUrlId() {
        return urlId;
    }

    public void setUrlId(Long urlId) {
        this.urlId = urlId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<BusinessEntity> getBusinessEntityList() {
        return businessEntityList;
    }

    public void setBusinessEntityList(List<BusinessEntity> businessEntityList) {
        this.businessEntityList = businessEntityList;
    }

    public CountryEntity getUrlCountryOfActivity() {
        return urlCountryOfActivity;
    }

    public void setUrlCountryOfActivity(CountryEntity urlCountryOfActivity) {
        this.urlCountryOfActivity = urlCountryOfActivity;
    }

    @Override
    public int hashCode() {
        return urlId != null ? urlId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof UrlEntity ? object.hashCode()==hashCode() : false;
    }

    @Override
	public String toString() {
		return "UrlEntity [urlId=" + urlId + ", url=" + url + "]";
	}
}

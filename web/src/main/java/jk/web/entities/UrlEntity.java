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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "urls", catalog = "jk", schema = "")
@NamedQueries({
    @NamedQuery(name = "UrlEntity.findAll", query = "SELECT u FROM UrlEntity u")})
public class UrlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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

    enum UrlStatus{
    	UNKNOWN,
    	ACTIVE,
    	INACTIVE
    }
    @Basic(optional = false)
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private UrlStatus urlStatus = UrlStatus.UNKNOWN;

    @ManyToMany(mappedBy = "urlEntityList")
    private List<BusinessEntity> businessEntityList;

    public UrlEntity() {
    }

    public UrlEntity(Long urlId) {
        this.urlId = urlId;
    }

    public UrlEntity(Long urlId, String url, UrlStatus urlStatus) {
        this.urlId = urlId;
        this.url = url;
        this.urlStatus = urlStatus;
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

    public UrlStatus getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(UrlStatus urlStatus) {
        this.urlStatus = urlStatus;
    }

    public List<BusinessEntity> getBusinessEntityList() {
        return businessEntityList;
    }

    public void setBusinessEntityList(List<BusinessEntity> businessEntityList) {
        this.businessEntityList = businessEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (urlId != null ? urlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UrlEntity)) {
            return false;
        }
        UrlEntity other = (UrlEntity) object;
        if ((this.urlId == null && other.urlId != null) || (this.urlId != null && !this.urlId.equals(other.urlId))) {
            return false;
        }
        return true;
    }

    @Override
	public String toString() {
		return "UrlEntity [urlId=" + urlId + ", url=" + url + ", urlStatus="
				+ urlStatus + "]";
	}
    
}

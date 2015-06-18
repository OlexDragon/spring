/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.business;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "business_has_urls", catalog = "jk", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BusinessHasUrls.findAll", query = "SELECT b FROM BusinessHasUrls b"),
    @NamedQuery(name = "BusinessHasUrls.findByBusinessId", query = "SELECT b FROM BusinessHasUrls b WHERE b.businessHasUrlsPK.businessId = :businessId"),
    @NamedQuery(name = "BusinessHasUrls.findByUrlId", query = "SELECT b FROM BusinessHasUrls b WHERE b.businessHasUrlsPK.urlId = :urlId"),
    @NamedQuery(name = "BusinessHasUrls.findByUrlStatus", query = "SELECT b FROM BusinessHasUrls b WHERE b.urlStatus = :urlStatus")})
public class BusinessHasUrls implements Serializable {
	private static final long serialVersionUID = -3898665521402402851L;

	@EmbeddedId
    protected BusinessHasUrlsPK businessHasUrlsPK;

    @Basic(optional = false)
    @NotNull
    @Column(name = "url_status", nullable = false)
    private int urlStatus;

    @JoinColumn(name = "url_id", referencedColumnName = "url_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BusinessUrlEntity urlEntity;

    @JoinColumn(name = "business_id", referencedColumnName = "business_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BusinessEntity businessEntity;

    public BusinessHasUrls() {
    }

    public BusinessHasUrls(BusinessHasUrlsPK businessHasUrlsPK) {
        this.businessHasUrlsPK = businessHasUrlsPK;
    }

    public BusinessHasUrls(BusinessHasUrlsPK businessHasUrlsPK, int urlStatus) {
        this.businessHasUrlsPK = businessHasUrlsPK;
        this.urlStatus = urlStatus;
    }

    public BusinessHasUrls(int businessId, int urlId) {
        this.businessHasUrlsPK = new BusinessHasUrlsPK(businessId, urlId);
    }

    public BusinessHasUrlsPK getBusinessHasUrlsPK() {
        return businessHasUrlsPK;
    }

    public void setBusinessHasUrlsPK(BusinessHasUrlsPK businessHasUrlsPK) {
        this.businessHasUrlsPK = businessHasUrlsPK;
    }

    public int getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(int urlStatus) {
        this.urlStatus = urlStatus;
    }

    public BusinessUrlEntity getUrlEntity() {
        return urlEntity;
    }

    public void setUrlEntity(BusinessUrlEntity urlEntity) {
        this.urlEntity = urlEntity;
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (businessHasUrlsPK != null ? businessHasUrlsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessHasUrls)) {
            return false;
        }
        BusinessHasUrls other = (BusinessHasUrls) object;
        if ((this.businessHasUrlsPK == null && other.businessHasUrlsPK != null) || (this.businessHasUrlsPK != null && !this.businessHasUrlsPK.equals(other.businessHasUrlsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.business.BusinessHasUrls[ businessHasUrlsPK=" + businessHasUrlsPK + " ]";
    }
    
}

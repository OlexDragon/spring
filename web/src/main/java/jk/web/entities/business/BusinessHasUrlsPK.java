/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.business;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Oleksandr
 */
@Embeddable
public class BusinessHasUrlsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "business_id", nullable = false)
    private int businessId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "url_id", nullable = false)
    private int urlId;

    public BusinessHasUrlsPK() {
    }

    public BusinessHasUrlsPK(int businessId, int urlId) {
        this.businessId = businessId;
        this.urlId = urlId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getUrlId() {
        return urlId;
    }

    public void setUrlId(int urlId) {
        this.urlId = urlId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) businessId;
        hash += (int) urlId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessHasUrlsPK)) {
            return false;
        }
        BusinessHasUrlsPK other = (BusinessHasUrlsPK) object;
        if (this.businessId != other.businessId) {
            return false;
        }
        if (this.urlId != other.urlId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.business.BusinessHasUrlsPK[ businessId=" + businessId + ", urlId=" + urlId + " ]";
    }
    
}

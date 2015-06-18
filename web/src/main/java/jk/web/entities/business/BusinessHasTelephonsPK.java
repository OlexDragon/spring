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
public class BusinessHasTelephonsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "business_id", nullable = false)
    private int businessId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telephon_id", nullable = false)
    private int telephonId;

    public BusinessHasTelephonsPK() {
    }

    public BusinessHasTelephonsPK(int businessId, int telephonId) {
        this.businessId = businessId;
        this.telephonId = telephonId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getTelephonId() {
        return telephonId;
    }

    public void setTelephonId(int telephonId) {
        this.telephonId = telephonId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) businessId;
        hash += (int) telephonId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessHasTelephonsPK)) {
            return false;
        }
        BusinessHasTelephonsPK other = (BusinessHasTelephonsPK) object;
        if (this.businessId != other.businessId) {
            return false;
        }
        if (this.telephonId != other.telephonId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.business.BusinessHasTelephonsPK[ businessId=" + businessId + ", telephonId=" + telephonId + " ]";
    }
    
}

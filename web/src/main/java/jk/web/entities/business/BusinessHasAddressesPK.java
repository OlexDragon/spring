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
public class BusinessHasAddressesPK implements Serializable {
	private static final long serialVersionUID = 2118991185223685252L;

	@Basic(optional = false)
    @NotNull
    @Column(name = "addsress_id", nullable = false)
    private int addsressId;

	@Basic(optional = false)
    @NotNull
    @Column(name = "business_id", nullable = false)
    private int businessId;

    public BusinessHasAddressesPK() {
    }

    public BusinessHasAddressesPK(int addsressId, int businessId) {
        this.addsressId = addsressId;
        this.businessId = businessId;
    }

    public int getAddsressId() {
        return addsressId;
    }

    public void setAddsressId(int addsressId) {
        this.addsressId = addsressId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) addsressId;
        hash += (int) businessId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessHasAddressesPK)) {
            return false;
        }
        BusinessHasAddressesPK other = (BusinessHasAddressesPK) object;
        if (this.addsressId != other.addsressId) {
            return false;
        }
        if (this.businessId != other.businessId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.business.BusinessHasAddressesPK[ addsressId=" + addsressId + ", businessId=" + businessId + " ]";
    }
    
}

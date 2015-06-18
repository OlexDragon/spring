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
public class BusinessHasContactEmailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "contact_emails_email_id", nullable = false)
    private int contactEmailsEmailId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "business_business_id", nullable = false)
    private int businessBusinessId;

    public BusinessHasContactEmailsPK() {
    }

    public BusinessHasContactEmailsPK(int contactEmailsEmailId, int businessBusinessId) {
        this.contactEmailsEmailId = contactEmailsEmailId;
        this.businessBusinessId = businessBusinessId;
    }

    public int getContactEmailsEmailId() {
        return contactEmailsEmailId;
    }

    public void setContactEmailsEmailId(int contactEmailsEmailId) {
        this.contactEmailsEmailId = contactEmailsEmailId;
    }

    public int getBusinessBusinessId() {
        return businessBusinessId;
    }

    public void setBusinessBusinessId(int businessBusinessId) {
        this.businessBusinessId = businessBusinessId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) contactEmailsEmailId;
        hash += (int) businessBusinessId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessHasContactEmailsPK)) {
            return false;
        }
        BusinessHasContactEmailsPK other = (BusinessHasContactEmailsPK) object;
        if (this.contactEmailsEmailId != other.contactEmailsEmailId) {
            return false;
        }
        if (this.businessBusinessId != other.businessBusinessId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.business.BusinessHasContactEmailsPK[ contactEmailsEmailId=" + contactEmailsEmailId + ", businessBusinessId=" + businessBusinessId + " ]";
    }
    
}

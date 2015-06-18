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
import jk.web.entities.ContactEmailEntity;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "business_has_contact_emails", catalog = "jk", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BusinessHasContactEmails.findAll", query = "SELECT b FROM BusinessHasContactEmails b"),
    @NamedQuery(name = "BusinessHasContactEmails.findByContactEmailsEmailId", query = "SELECT b FROM BusinessHasContactEmails b WHERE b.businessHasContactEmailsPK.contactEmailsEmailId = :contactEmailsEmailId"),
    @NamedQuery(name = "BusinessHasContactEmails.findByBusinessBusinessId", query = "SELECT b FROM BusinessHasContactEmails b WHERE b.businessHasContactEmailsPK.businessBusinessId = :businessBusinessId"),
    @NamedQuery(name = "BusinessHasContactEmails.findByEmailStatus", query = "SELECT b FROM BusinessHasContactEmails b WHERE b.emailStatus = :emailStatus")})
public class BusinessHasContactEmails implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BusinessHasContactEmailsPK businessHasContactEmailsPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "email_status", nullable = false)
    private int emailStatus;
    @JoinColumn(name = "contact_emails_email_id", referencedColumnName = "email_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ContactEmailEntity contactEmailEntity;
    @JoinColumn(name = "business_business_id", referencedColumnName = "business_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BusinessEntity businessEntity;

    public BusinessHasContactEmails() {
    }

    public BusinessHasContactEmails(BusinessHasContactEmailsPK businessHasContactEmailsPK) {
        this.businessHasContactEmailsPK = businessHasContactEmailsPK;
    }

    public BusinessHasContactEmails(BusinessHasContactEmailsPK businessHasContactEmailsPK, int emailStatus) {
        this.businessHasContactEmailsPK = businessHasContactEmailsPK;
        this.emailStatus = emailStatus;
    }

    public BusinessHasContactEmails(int contactEmailsEmailId, int businessBusinessId) {
        this.businessHasContactEmailsPK = new BusinessHasContactEmailsPK(contactEmailsEmailId, businessBusinessId);
    }

    public BusinessHasContactEmailsPK getBusinessHasContactEmailsPK() {
        return businessHasContactEmailsPK;
    }

    public void setBusinessHasContactEmailsPK(BusinessHasContactEmailsPK businessHasContactEmailsPK) {
        this.businessHasContactEmailsPK = businessHasContactEmailsPK;
    }

    public int getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(int emailStatus) {
        this.emailStatus = emailStatus;
    }

    public ContactEmailEntity getContactEmailEntity() {
        return contactEmailEntity;
    }

    public void setContactEmailEntity(ContactEmailEntity contactEmailEntity) {
        this.contactEmailEntity = contactEmailEntity;
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
        hash += (businessHasContactEmailsPK != null ? businessHasContactEmailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessHasContactEmails)) {
            return false;
        }
        BusinessHasContactEmails other = (BusinessHasContactEmails) object;
        if ((this.businessHasContactEmailsPK == null && other.businessHasContactEmailsPK != null) || (this.businessHasContactEmailsPK != null && !this.businessHasContactEmailsPK.equals(other.businessHasContactEmailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.business.BusinessHasContactEmails[ businessHasContactEmailsPK=" + businessHasContactEmailsPK + " ]";
    }
    
}

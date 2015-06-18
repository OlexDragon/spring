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
import jk.web.entities.AddressEntity;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "business_has_addresses", catalog = "jk", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BusinessHasAddresses.findAll", query = "SELECT b FROM BusinessHasAddresses b"),
    @NamedQuery(name = "BusinessHasAddresses.findByAddsressId", query = "SELECT b FROM BusinessHasAddresses b WHERE b.businessHasAddressesPK.addsressId = :addsressId"),
    @NamedQuery(name = "BusinessHasAddresses.findByBusinessId", query = "SELECT b FROM BusinessHasAddresses b WHERE b.businessHasAddressesPK.businessId = :businessId"),
    @NamedQuery(name = "BusinessHasAddresses.findByAddressStatus", query = "SELECT b FROM BusinessHasAddresses b WHERE b.addressStatus = :addressStatus")})
public class BusinessHasAddresses implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected BusinessHasAddressesPK businessHasAddressesPK;

    @Basic(optional = false)
    @NotNull
    @Column(name = "address_status", nullable = false)
    private int addressStatus;

    @JoinColumn(name = "business_id", referencedColumnName = "business_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BusinessEntity businessEntity;

    @JoinColumn(name = "addsress_id", referencedColumnName = "addsress_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BusinessAddressEntity addressEntity;

    public BusinessHasAddresses() {
    }

    public BusinessHasAddresses(BusinessHasAddressesPK businessHasAddressesPK) {
        this.businessHasAddressesPK = businessHasAddressesPK;
    }

    public BusinessHasAddresses(BusinessHasAddressesPK businessHasAddressesPK, int addressStatus) {
        this.businessHasAddressesPK = businessHasAddressesPK;
        this.addressStatus = addressStatus;
    }

    public BusinessHasAddresses(int addsressId, int businessId) {
        this.businessHasAddressesPK = new BusinessHasAddressesPK(addsressId, businessId);
    }

    public BusinessHasAddressesPK getBusinessHasAddressesPK() {
        return businessHasAddressesPK;
    }

    public void setBusinessHasAddressesPK(BusinessHasAddressesPK businessHasAddressesPK) {
        this.businessHasAddressesPK = businessHasAddressesPK;
    }

    public int getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(int addressStatus) {
        this.addressStatus = addressStatus;
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(BusinessAddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (businessHasAddressesPK != null ? businessHasAddressesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessHasAddresses)) {
            return false;
        }
        BusinessHasAddresses other = (BusinessHasAddresses) object;
        if ((this.businessHasAddressesPK == null && other.businessHasAddressesPK != null) || (this.businessHasAddressesPK != null && !this.businessHasAddressesPK.equals(other.businessHasAddressesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.business.BusinessHasAddresses[ businessHasAddressesPK=" + businessHasAddressesPK + " ]";
    }
    
}

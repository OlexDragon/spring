package jk.web.entities.business;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import jk.web.entities.AddressEntity;
import jk.web.entities.AddressEntity.AddressStatus;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "business_has_addresses", catalog = "jk", schema = "")
@XmlRootElement
public class BusinessHasAddresses implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected BusinessHasAddressesPK businessHasAddressesPK;

    @Column(name = "address_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AddressStatus addressStatus = AddressStatus.ACTIVE;

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

    public BusinessHasAddresses(BusinessHasAddressesPK businessHasAddressesPK, AddressStatus addressStatus) {
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

    public AddressStatus getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(AddressStatus addressStatus) {
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
        return businessHasAddressesPK != null ? businessHasAddressesPK.hashCode() : super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof BusinessHasAddresses ? object.hashCode() == hashCode() : false;
    }

    @Override
    public String toString() {
        return "jk.web.entities.business.BusinessHasAddresses[ businessHasAddressesPK=" + businessHasAddressesPK + " ]";
    }
    
}

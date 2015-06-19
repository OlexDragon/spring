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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "business_has_telephons", catalog = "jk", schema = "")
@XmlRootElement
public class BusinessHasTelephons implements Serializable {
	private static final long serialVersionUID = 7083916420442772568L;

	@EmbeddedId
    protected BusinessHasTelephonsPK businessHasTelephonsPK;

    @Basic(optional = false)
    @NotNull
    @Column(name = "telephon_status", nullable = false)
    private int telephonStatus;

    @JoinColumn(name = "telephon_id", referencedColumnName = "telephon_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BusinessTelephonEntity telephonEntity;

    @JoinColumn(name = "business_id", referencedColumnName = "business_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BusinessEntity businessEntity;

    public BusinessHasTelephons() {
    }

    public BusinessHasTelephons(BusinessHasTelephonsPK businessHasTelephonsPK) {
        this.businessHasTelephonsPK = businessHasTelephonsPK;
    }

    public BusinessHasTelephons(BusinessHasTelephonsPK businessHasTelephonsPK, int telephonStatus) {
        this.businessHasTelephonsPK = businessHasTelephonsPK;
        this.telephonStatus = telephonStatus;
    }

    public BusinessHasTelephons(int businessId, int telephonId) {
        this.businessHasTelephonsPK = new BusinessHasTelephonsPK(businessId, telephonId);
    }

    public BusinessHasTelephonsPK getBusinessHasTelephonsPK() {
        return businessHasTelephonsPK;
    }

    public void setBusinessHasTelephonsPK(BusinessHasTelephonsPK businessHasTelephonsPK) {
        this.businessHasTelephonsPK = businessHasTelephonsPK;
    }

    public int getTelephonStatus() {
        return telephonStatus;
    }

    public void setTelephonStatus(int telephonStatus) {
        this.telephonStatus = telephonStatus;
    }

    public BusinessTelephonEntity getTelephonEntity() {
        return telephonEntity;
    }

    public void setTelephonEntity(BusinessTelephonEntity telephonEntity) {
        this.telephonEntity = telephonEntity;
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
        hash += (businessHasTelephonsPK != null ? businessHasTelephonsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessHasTelephons)) {
            return false;
        }
        BusinessHasTelephons other = (BusinessHasTelephons) object;
        if ((this.businessHasTelephonsPK == null && other.businessHasTelephonsPK != null) || (this.businessHasTelephonsPK != null && !this.businessHasTelephonsPK.equals(other.businessHasTelephonsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.business.BusinessHasTelephons[ businessHasTelephonsPK=" + businessHasTelephonsPK + " ]";
    }
    
}

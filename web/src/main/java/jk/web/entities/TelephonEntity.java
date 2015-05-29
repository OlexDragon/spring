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
@Table(name = "telephons", catalog = "jk", schema = "")
@NamedQueries({
    @NamedQuery(name = "TelephonEntity.findAll", query = "SELECT t FROM TelephonEntity t")})
public class TelephonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "telephon_id")
    private Integer telephonId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "telephon")
    private String telephon;

    enum TelephonType{
    	UNKNOWN,
    	HOME,
    	WORK,
    	CELL
    }
    @Basic(optional = false)
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "telephon_type")
    private TelephonType telephonType = TelephonType.UNKNOWN;

    enum TelephonStatus{
    	INACTIVE,
    	ACTIVE
    }
    @Basic(optional = false)
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "telephon_status")
    private TelephonStatus telephonStatus = TelephonStatus.ACTIVE;

    @ManyToMany(mappedBy = "telephonEntityList")
    private List<BusinessEntity> businessEntityList;

    public TelephonEntity() {
    }

    public TelephonEntity(Integer telephonId) {
        this.telephonId = telephonId;
    }

    public TelephonEntity(Integer telephonId, String telephon, TelephonType telephonType, TelephonStatus telephonStatus) {
        this.telephonId = telephonId;
        this.telephon = telephon;
        this.telephonType = telephonType;
        this.telephonStatus = telephonStatus;
    }

    public Integer getTelephonId() {
        return telephonId;
    }

    public void setTelephonId(Integer telephonId) {
        this.telephonId = telephonId;
    }

    public String getTelephon() {
        return telephon;
    }

    public void setTelephon(String telephon) {
        this.telephon = telephon;
    }

    public TelephonType getTelephonType() {
        return telephonType;
    }

    public void setTelephonType(TelephonType telephonType) {
        this.telephonType = telephonType;
    }

    public TelephonStatus getTelephonStatus() {
        return telephonStatus;
    }

    public void setTelephonStatus(TelephonStatus telephonStatus) {
        this.telephonStatus = telephonStatus;
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
        hash += (telephonId != null ? telephonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TelephonEntity)) {
            return false;
        }
        TelephonEntity other = (TelephonEntity) object;
        if ((this.telephonId == null && other.telephonId != null) || (this.telephonId != null && !this.telephonId.equals(other.telephonId))) {
            return false;
        }
        return true;
    }

    @Override
	public String toString() {
		return "TelephonEntity [telephonId=" + telephonId + ", telephon="
				+ telephon + ", telephonType=" + telephonType
				+ ", telephonStatus=" + telephonStatus + "]";
	}
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "reference_numbers", catalog = "jk", schema = "")
public class ReferenceNumberEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "reference_number_id")
    private Long referenceNumberId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "reference_number")
    private String referenceNumber;

     @Column(name = "reference_number_creation_date", insertable=false, updatable=false)
    private Timestamp referenceNumberCreationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceNumberEntity", fetch = FetchType.EAGER)
    private List<ContactUsEntity> contactUsList;

    public ReferenceNumberEntity() {
    }

    public ReferenceNumberEntity(Long referenceNumberId) {
        this.referenceNumberId = referenceNumberId;
    }

    public ReferenceNumberEntity(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Long getReferenceNumberId() {
        return referenceNumberId;
    }

    public void setReferenceNumberId(Long referenceNumberId) {
        this.referenceNumberId = referenceNumberId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Timestamp getReferenceNumberCreationDate() {
        return referenceNumberCreationDate;
    }

    public void setReferenceNumberCreationDate(Timestamp referenceNumberCreationDate) {
        this.referenceNumberCreationDate = referenceNumberCreationDate;
    }

    public List<ContactUsEntity> getContactUsList() {
        return contactUsList;
    }

    public void setContactUsList(List<ContactUsEntity> contactUsList) {
        this.contactUsList = contactUsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (referenceNumberId != null ? referenceNumberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReferenceNumberEntity)) {
            return false;
        }
        ReferenceNumberEntity other = (ReferenceNumberEntity) object;
        if ((this.referenceNumberId == null && other.referenceNumberId != null) || (this.referenceNumberId != null && !this.referenceNumberId.equals(other.referenceNumberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return referenceNumber;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.entities.business;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import jk.web.entities.AddressEntity;
import jk.web.entities.EmailEntity;
import jk.web.entities.user.UsersHasBusinesses;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "business", catalog = "jk", schema = "")
@XmlRootElement
public class BusinessEntity implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessEntity", fetch = FetchType.LAZY)
    private List<BusinessHasContactEmails> businessHasContactEmailsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessEntity", fetch = FetchType.LAZY)
    private List<BusinessHasTelephons> businessHasTelephonsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessEntity", fetch = FetchType.LAZY)
    private List<BusinessHasUrls> businessHasUrlsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessEntity", fetch = FetchType.LAZY)
    private List<BusinessHasAddresses> businessHasAddressesList;
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "business_id")
    private Long businessId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "company_name", nullable = false, length = 145)
    private String companyName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 21)
    @Column(name = "VAT_number")
    private String vatNumber;

    @JoinTable(name = "business_has_telephons", joinColumns = {
        @JoinColumn(name = "business_id", referencedColumnName = "business_id")}, inverseJoinColumns = {
        @JoinColumn(name = "telephon_id", referencedColumnName = "telephon_id")})
    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @NotFound(action=NotFoundAction.IGNORE)
    private List<BusinessTelephonEntity> telephonEntityList;

    @JoinTable(name = "business_has_addresses",
    		joinColumns = { @JoinColumn(
    				name = "business_id",
    				referencedColumnName = "business_id")},
    		inverseJoinColumns = {@JoinColumn(
    				name = "addsress_id",
    				referencedColumnName = "addsress_id")})
    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @NotFound(action=NotFoundAction.IGNORE)
    private List<AddressEntity> addressEntityList;

    @JoinTable(name = "business_has_contact_emails", joinColumns = {
            @JoinColumn(name = "business_business_id", referencedColumnName = "business_id")}, inverseJoinColumns = {
            @JoinColumn(name = "contact_emails_email_id", referencedColumnName = "email_id")})
    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @NotFound(action=NotFoundAction.IGNORE)
    private List<EmailEntity> contactEmailEntityList;

    @JoinTable(name = "business_has_urls", joinColumns = {
    		@JoinColumn(name = "business_id", referencedColumnName = "business_id")}, inverseJoinColumns = {
            @JoinColumn(name = "url_id", referencedColumnName = "url_id")})
    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @NotFound(action=NotFoundAction.IGNORE)
    private List<BusinessUrlEntity> urlEntityList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessEntity")
    private List<UsersHasBusinesses> usersHasBusinessEntityList;
 
    public BusinessEntity() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BusinessEntity(Long businessId) {
        this.businessId = businessId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vATnumber) {
        this.vatNumber = vATnumber;
    }

    public List<BusinessTelephonEntity> getTelephonEntityList() {
        return telephonEntityList;
    }

    public void setTelephonEntityList(List<BusinessTelephonEntity> telephonEntityList) {
        this.telephonEntityList = telephonEntityList;
    }

    public List<AddressEntity> getAddressEntityList() {
        return addressEntityList;
    }

    public void setAddressEntityList(List<AddressEntity> addressEntityList) {
        this.addressEntityList = addressEntityList;
    }

    public List<EmailEntity> getContactEmailEntityList() {
        return contactEmailEntityList;
    }

    public void setContactEmailEntityList(List<EmailEntity> contactEmailEntityList) {
        this.contactEmailEntityList = contactEmailEntityList;
    }

    public List<BusinessUrlEntity> getUrlEntityList() {
        return urlEntityList;
    }

    public void setUrlEntityList(List<BusinessUrlEntity> urlEntityList) {
        this.urlEntityList = urlEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (businessId != null ? businessId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessEntity)) {
            return false;
        }
        BusinessEntity other = (BusinessEntity) object;
        if ((this.businessId == null && other.businessId != null) || (this.businessId != null && !this.businessId.equals(other.businessId))) {
            return false;
        }
        return true;
    }

    @XmlTransient
    public List<UsersHasBusinesses> getUsersHasBusinessEntityList() {
        return usersHasBusinessEntityList;
    }

    public void setUsersHasBusinessEntityList(List<UsersHasBusinesses> usersHasBusinessEntityList) {
        this.usersHasBusinessEntityList = usersHasBusinessEntityList;
    }

    @Override
	public String toString() {
		return "\n\tBusinessEntity [businessId=" + businessId + ", companyName="
				+ companyName + ", vATnumber=" + vatNumber
				+ ", telephonEntityList=" + telephonEntityList
				+ ", addressEntityList=" + addressEntityList
				+ ", contactEmailEntityList=" + contactEmailEntityList
				+ ", urlEntityList=" + urlEntityList + "]";
	}

    @XmlTransient
    public List<BusinessHasContactEmails> getBusinessHasContactEmailsList() {
        return businessHasContactEmailsList;
    }

    public void setBusinessHasContactEmailsList(List<BusinessHasContactEmails> businessHasContactEmailsList) {
        this.businessHasContactEmailsList = businessHasContactEmailsList;
    }

    @XmlTransient
    public List<BusinessHasTelephons> getBusinessHasTelephonsList() {
        return businessHasTelephonsList;
    }

    public void setBusinessHasTelephonsList(List<BusinessHasTelephons> businessHasTelephonsList) {
        this.businessHasTelephonsList = businessHasTelephonsList;
    }

    @XmlTransient
    public List<BusinessHasUrls> getBusinessHasUrlsList() {
        return businessHasUrlsList;
    }

    public void setBusinessHasUrlsList(List<BusinessHasUrls> businessHasUrlsList) {
        this.businessHasUrlsList = businessHasUrlsList;
    }

    @XmlTransient
    public List<BusinessHasAddresses> getBusinessHasAddressesList() {
        return businessHasAddressesList;
    }

    public void setBusinessHasAddressesList(List<BusinessHasAddresses> businessHasAddressesList) {
        this.businessHasAddressesList = businessHasAddressesList;
    }
}

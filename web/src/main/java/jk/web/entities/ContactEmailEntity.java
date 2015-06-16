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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import jk.web.entities.user.LoginEntity;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "contact_emails", catalog = "jk", schema = "")
public class ContactEmailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "email_id")
    private Long emailId;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "contact_email")
    private String email;

    @Column(name = "email_creation_date",insertable=false, updatable=false)
    private Timestamp emailCreationDate;

    public enum EmailStatus{
    	TO_CONTACT,
    	TO_CONFIRM,
    	CONFIRMED
    }
    @Column(name = "email_status")
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private EmailStatus emailStatus = EmailStatus.TO_CONFIRM;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contactEmailEntity", fetch = FetchType.EAGER)
    private List<ContactUsEntity> contactUsList;

    @ManyToMany(mappedBy = "contactEmailEntityList")
    private List<BusinessEntity> businessEntityList;

    @JoinTable(name = "logins_has_contact_emails", joinColumns = {
    		@JoinColumn(name = "contact_emails_email_id", referencedColumnName = "email_id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "logins_login_id", referencedColumnName = "login_id", nullable = false)})
    @ManyToMany
    private List<LoginEntity> loginEntityList;

    public ContactEmailEntity() {
    }

    public ContactEmailEntity(Long emailId) {
        this.emailId = emailId;
    }

    public ContactEmailEntity(String email) {
        this.email = email;
    }

    public ContactEmailEntity(String email, EmailStatus emailStatus) {
        this.email = email;
        this.emailStatus = emailStatus;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getEmailCreationDate() {
        return emailCreationDate;
    }

    public void setEmailCreationDate(Timestamp emailCreationDate) {
        this.emailCreationDate = emailCreationDate;
    }

    public EmailStatus getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(EmailStatus emailStatus) {
        this.emailStatus = emailStatus;
    }

    public List<ContactUsEntity> getContactUsList() {
        return contactUsList;
    }

    public void setContactUsList(List<ContactUsEntity> contactUsList) {
        this.contactUsList = contactUsList;
    }

	public List<BusinessEntity> getBusinessEntityList() {
		return businessEntityList;
	}

	public void setBusinessEntityList(List<BusinessEntity> businessEntityList) {
		this.businessEntityList = businessEntityList;
	}

	public List<LoginEntity> getLoginEntityList() {
		return loginEntityList;
	}

	public void setLoginEntityList(List<LoginEntity> loginEntityList) {
		this.loginEntityList = loginEntityList;
	}

    @Override
    public int hashCode() {
        return emailId != null ? emailId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ContactEmailEntity ? object.hashCode()==hashCode() : false;
    }

	@Override
	public String toString() {
		return "/n\tContactEmailEntity [emailId=" + emailId + ", email=" + email
				+ ", emailCreationDate=" + emailCreationDate + ", emailStatus="
				+ emailStatus + "]";
	}
}

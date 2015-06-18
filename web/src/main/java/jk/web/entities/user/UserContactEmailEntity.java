/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import jk.web.entities.ContactEmailEntity;
import jk.web.entities.ContactUsEntity;
import jk.web.entities.business.BusinessHasContactEmails;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "contact_emails", catalog = "jk", schema = "")
public class UserContactEmailEntity extends ContactEmailEntity {
	private static final long serialVersionUID = -39156753727725349L;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "contactEmailEntity", fetch = FetchType.LAZY)
    private List<UserHasContactEmails> userHasContactEmailsCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contactEmailEntity", fetch = FetchType.LAZY)
    private List<BusinessHasContactEmails> businessHasContactEmailsList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contactEmailEntity", fetch = FetchType.EAGER)
    private List<ContactUsEntity> contactUsList;

    @JoinTable(name = "user_has_contact_emails", joinColumns = {
    		@JoinColumn(name = "email_id", referencedColumnName = "email_id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "login_id", referencedColumnName = "login_id", nullable = false)})
    @ManyToMany(fetch=FetchType.LAZY)
    private List<LoginEntity> loginEntityList;

    public UserContactEmailEntity() {
	}

    public UserContactEmailEntity(String email) {
		super(email);
	}

	public UserContactEmailEntity(String email, EmailStatus emailStatus) {
		super(email, emailStatus);
	}

	@XmlTransient
    public List<ContactUsEntity> getContactUsList() {
        return contactUsList;
    }

    public void setContactUsList(List<ContactUsEntity> contactUsList) {
        this.contactUsList = contactUsList;
    }

	public List<LoginEntity> getLoginEntityList() {
		return loginEntityList;
	}

	public void setLoginEntityList(List<LoginEntity> loginEntityList) {
		this.loginEntityList = loginEntityList;
	}

    @XmlTransient
    public List<BusinessHasContactEmails> getBusinessHasContactEmailsList() {
        return businessHasContactEmailsList;
    }

    public void setBusinessHasContactEmailsList(List<BusinessHasContactEmails> businessHasContactEmailsList) {
        this.businessHasContactEmailsList = businessHasContactEmailsList;
    }

    @XmlTransient
    public List<UserHasContactEmails> getUserHasContactEmailsCollection() {
        return userHasContactEmailsCollection;
    }

    public void setUserHasContactEmailsCollection(List<UserHasContactEmails> userHasContactEmailsCollection) {
        this.userHasContactEmailsCollection = userHasContactEmailsCollection;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.business;

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

import com.fasterxml.jackson.annotation.JsonProperty;

import jk.web.entities.ContactEmailEntity;
import jk.web.entities.user.LoginEntity;
import jk.web.entities.user.UserHasContactEmails;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "contact_emails", catalog = "jk", schema = "")
public class BusinessContactEmailEntity extends ContactEmailEntity{
    private static final long serialVersionUID = 1L;

    @JoinTable(name = "user_has_contact_emails", joinColumns = {
    		@JoinColumn(name = "email_id", referencedColumnName = "email_id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "login_id", referencedColumnName = "login_id", nullable = false)})
    @ManyToMany(fetch=FetchType.LAZY)
    private List<LoginEntity> loginEntityList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contactEmailEntity", fetch = FetchType.LAZY)
    private List<BusinessHasContactEmails> businessHasContactEmailsList;

    @XmlTransient
    public List<BusinessHasContactEmails> getBusinessHasContactEmailsList() {
        return businessHasContactEmailsList;
    }

    public void setBusinessHasContactEmailsList(List<BusinessHasContactEmails> businessHasContactEmailsList) {
        this.businessHasContactEmailsList = businessHasContactEmailsList;
    }

    @XmlTransient
	public List<LoginEntity> getLoginEntityList() {
		return loginEntityList;
	}

	public void setLoginEntityList(List<LoginEntity> loginEntityList) {
		this.loginEntityList = loginEntityList;
	}
}

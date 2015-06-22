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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jk.web.entities.ContactUsEntity.ContactUsStatus;
import jk.web.entities.EmailEntity;
import jk.web.entities.ReferenceNumberEntity;
import jk.web.entities.statistic.IpAddressEntity;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "contact_emails", catalog = "jk", schema = "")
public class UserEmailEntity extends EmailEntity {
	private static final long serialVersionUID = -39156753727725349L;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "emailEntity", fetch = FetchType.LAZY)
	@JsonManagedReference
    private List<UserHasEmails> hasEmails;

    public UserEmailEntity() {
	}

    public UserEmailEntity(String email) {
		super(email);
	}

	public UserEmailEntity(String name, String subject, String message, IpAddressEntity findOneByIpAddress, ReferenceNumberEntity referenceNumberEntity, UserEmailEntity emailEntity, ContactUsStatus toAnswer) {
		super( name,  subject,  message,  findOneByIpAddress,  referenceNumberEntity,  emailEntity,  toAnswer);
	}

    @XmlTransient
    public List<UserHasEmails> getHasEmails() {
        return hasEmails;
    }

    public void setHasEmails(List<UserHasEmails> userHasEmailsList) {
        this.hasEmails = userHasEmailsList;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jk.web.entities.ContactUsEntity.ContactUsStatus;
import jk.web.entities.statistic.IpAddressEntity;
import jk.web.entities.user.UserEmailEntity;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "contact_emails", catalog = "jk", schema = "")
public class EmailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum EmailStatus{
    	TO_CONFIRM,
    	CONFIRMED,
    	ACTIVE,
    	DISABLED
    }

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

    public EmailEntity() {
    }

    public EmailEntity(Long emailId) {
        this.emailId = emailId;
    }

    public EmailEntity(String email) {
        this.email = email;
    }

    public EmailEntity(String name, String subject, String message, IpAddressEntity findOneByIpAddress, ReferenceNumberEntity referenceNumberEntity, UserEmailEntity emailEntity, ContactUsStatus toAnswer) {
		// TODO Auto-generated constructor stub
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

    @Override
    public int hashCode() {
        return emailId != null ? emailId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof EmailEntity ? object.hashCode()==hashCode() : false;
    }

	@Override
	public String toString() {
		return "/n\tContactEmailEntity [emailId=" + emailId + ", email=" + email
				+ ", emailCreationDate=" + emailCreationDate + "]";
	}
}

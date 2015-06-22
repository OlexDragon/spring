/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.user;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jk.web.entities.EmailEntity;
import jk.web.entities.EmailEntity.EmailStatus;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "user_has_contact_emails", catalog = "jk", schema = "")
@XmlRootElement
public class UserHasEmails implements Serializable {
	private static final long serialVersionUID = -5638482750718458640L;

	@EmbeddedId
	@JsonProperty("key")
    protected UserHasEmailsPK userHasEmailsPK;

	@Basic(optional = false)
    @NotNull
    @Column(name = "email_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
	@JsonProperty("email_status")
    private EmailStatus emailStatus = EmailStatus.TO_CONFIRM;

	@JoinColumn(name = "login_id", referencedColumnName = "login_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonBackReference
    private LoginEntity loginEntity;

	@JoinColumn(name = "email_id", referencedColumnName = "email_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonBackReference
    private UserEmailEntity emailEntity;

    public UserHasEmails() {
    }

    public UserHasEmails(UserHasEmailsPK userHasContactEmailsPK) {
        this.userHasEmailsPK = userHasContactEmailsPK;
    }

    public UserHasEmails(UserHasEmailsPK userHasContactEmailsPK, EmailStatus emailStatus) {
        this.userHasEmailsPK = userHasContactEmailsPK;
        this.emailStatus = emailStatus;
    }

    public UserHasEmails(Long loginId, Long emailId) {
        this.userHasEmailsPK = new UserHasEmailsPK(loginId, emailId);
    }

    public UserHasEmailsPK getUserHasEmailsPK() {
        return userHasEmailsPK;
    }

    public void setUserHasEmailsPK(UserHasEmailsPK userHasContactEmailsPK) {
        this.userHasEmailsPK = userHasContactEmailsPK;
    }

    public EmailStatus getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(EmailStatus emailStatus) {
        this.emailStatus = emailStatus;
    }

    public LoginEntity getLoginEntity() {
        return loginEntity;
    }

    public void setLoginEntity(LoginEntity loginEntity) {
        this.loginEntity = loginEntity;
    }

    public EmailEntity getEmailEntity() {
        return emailEntity;
    }

    public void setEmailEntity(UserEmailEntity userEmailEntity) {
        this.emailEntity = userEmailEntity;
    }

    @Override
	public String toString() {
		return "\n\tUserHasEmails [userHasEmailsPK=" + userHasEmailsPK + ", emailStatus=" + emailStatus + ", emailEntity=" + emailEntity + "]";
	}

	@Override
	public int hashCode() {
		return 31 + ((userHasEmailsPK == null) ? 0 : userHasEmailsPK.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof UserHasEmails ? obj.hashCode() == hashCode() : false;
	}
    
}

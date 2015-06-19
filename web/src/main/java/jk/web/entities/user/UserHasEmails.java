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
    protected UserHasEmailsPK userHasContactEmailsPK;

	@Basic(optional = false)
    @NotNull
    @Column(name = "email_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EmailStatus emailStatus = EmailStatus.TO_CONFIRM;

	@JoinColumn(name = "login_id", referencedColumnName = "login_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private LoginEntity loginEntity;

	@JoinColumn(name = "email_id", referencedColumnName = "email_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private UserEmailEntity emailEntity;

    public UserHasEmails() {
    }

    public UserHasEmails(UserHasEmailsPK userHasContactEmailsPK) {
        this.userHasContactEmailsPK = userHasContactEmailsPK;
    }

    public UserHasEmails(UserHasEmailsPK userHasContactEmailsPK, EmailStatus emailStatus) {
        this.userHasContactEmailsPK = userHasContactEmailsPK;
        this.emailStatus = emailStatus;
    }

    public UserHasEmails(Long loginId, Long emailId) {
        this.userHasContactEmailsPK = new UserHasEmailsPK(loginId, emailId);
    }

    public UserHasEmailsPK getUserHasContactEmailsPK() {
        return userHasContactEmailsPK;
    }

    public void setUserHasContactEmailsPK(UserHasEmailsPK userHasContactEmailsPK) {
        this.userHasContactEmailsPK = userHasContactEmailsPK;
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
    public int hashCode() {
        int hash = 0;
        hash += (userHasContactEmailsPK != null ? userHasContactEmailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserHasEmails)) {
            return false;
        }
        UserHasEmails other = (UserHasEmails) object;
        if ((this.userHasContactEmailsPK == null && other.userHasContactEmailsPK != null) || (this.userHasContactEmailsPK != null && !this.userHasContactEmailsPK.equals(other.userHasContactEmailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.user.UserHasContactEmails[ userHasContactEmailsPK=" + userHasContactEmailsPK + " ]";
    }
    
}

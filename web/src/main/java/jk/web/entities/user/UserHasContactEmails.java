/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.user;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import jk.web.entities.ContactEmailEntity;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "user_has_contact_emails", catalog = "jk", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserHasContactEmails.findAll", query = "SELECT u FROM UserHasContactEmails u"),
    @NamedQuery(name = "UserHasContactEmails.findByLoginId", query = "SELECT u FROM UserHasContactEmails u WHERE u.userHasContactEmailsPK.loginId = :loginId"),
    @NamedQuery(name = "UserHasContactEmails.findByEmailId", query = "SELECT u FROM UserHasContactEmails u WHERE u.userHasContactEmailsPK.emailId = :emailId"),
    @NamedQuery(name = "UserHasContactEmails.findByEmailStatus", query = "SELECT u FROM UserHasContactEmails u WHERE u.emailStatus = :emailStatus")})
public class UserHasContactEmails implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserHasContactEmailsPK userHasContactEmailsPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "email_status", nullable = false)
    private int emailStatus;
    @JoinColumn(name = "login_id", referencedColumnName = "login_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LoginEntity loginEntity;
    @JoinColumn(name = "email_id", referencedColumnName = "email_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ContactEmailEntity contactEmailEntity;

    public UserHasContactEmails() {
    }

    public UserHasContactEmails(UserHasContactEmailsPK userHasContactEmailsPK) {
        this.userHasContactEmailsPK = userHasContactEmailsPK;
    }

    public UserHasContactEmails(UserHasContactEmailsPK userHasContactEmailsPK, int emailStatus) {
        this.userHasContactEmailsPK = userHasContactEmailsPK;
        this.emailStatus = emailStatus;
    }

    public UserHasContactEmails(int loginId, int emailId) {
        this.userHasContactEmailsPK = new UserHasContactEmailsPK(loginId, emailId);
    }

    public UserHasContactEmailsPK getUserHasContactEmailsPK() {
        return userHasContactEmailsPK;
    }

    public void setUserHasContactEmailsPK(UserHasContactEmailsPK userHasContactEmailsPK) {
        this.userHasContactEmailsPK = userHasContactEmailsPK;
    }

    public int getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(int emailStatus) {
        this.emailStatus = emailStatus;
    }

    public LoginEntity getLoginEntity() {
        return loginEntity;
    }

    public void setLoginEntity(LoginEntity loginEntity) {
        this.loginEntity = loginEntity;
    }

    public ContactEmailEntity getContactEmailEntity() {
        return contactEmailEntity;
    }

    public void setContactEmailEntity(ContactEmailEntity contactEmailEntity) {
        this.contactEmailEntity = contactEmailEntity;
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
        if (!(object instanceof UserHasContactEmails)) {
            return false;
        }
        UserHasContactEmails other = (UserHasContactEmails) object;
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

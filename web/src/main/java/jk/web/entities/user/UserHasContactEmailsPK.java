/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.user;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Oleksandr
 */
@Embeddable
public class UserHasContactEmailsPK implements Serializable {
	private static final long serialVersionUID = -23368926128270709L;

	@Basic(optional = false)
    @NotNull
    @Column(name = "login_id", nullable = false)
    private int loginId;

	@Basic(optional = false)
    @NotNull
    @Column(name = "email_id", nullable = false)
    private int emailId;

    public UserHasContactEmailsPK() {
    }

    public UserHasContactEmailsPK(int loginId, int emailId) {
        this.loginId = loginId;
        this.emailId = emailId;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) loginId;
        hash += (int) emailId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserHasContactEmailsPK)) {
            return false;
        }
        UserHasContactEmailsPK other = (UserHasContactEmailsPK) object;
        if (this.loginId != other.loginId) {
            return false;
        }
        if (this.emailId != other.emailId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.user.UserHasContactEmailsPK[ loginId=" + loginId + ", emailId=" + emailId + " ]";
    }
    
}

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
public class UserHasEmailsPK implements Serializable {
	private static final long serialVersionUID = -23368926128270709L;

	@Basic(optional = false)
    @NotNull
    @Column(name = "login_id", nullable = false)
    private Long loginId;

	@Basic(optional = false)
    @NotNull
    @Column(name = "email_id", nullable = false)
    private Long emailId;

    public UserHasEmailsPK() {
    }

    public UserHasEmailsPK(Long loginId, Long emailId) {
        this.loginId = loginId;
        this.emailId = emailId;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += loginId!=null ? 31* loginId.hashCode() : 31;
        hash += emailId!=null ? emailId.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof UserHasEmailsPK ? object.hashCode()==hashCode() : false;
    }

    @Override
	public String toString() {
		return "\n\tUserHasEmailsPK [loginId=" + loginId + ", emailId=" + emailId + "]";
	}
    
}

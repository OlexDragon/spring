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
public class UsersHasUrlsPK implements Serializable {
	private static final long serialVersionUID = -8955994009598422791L;

	@Basic(optional = false)
    @NotNull
    @Column(name = "users_login_id", nullable = false)
    private int usersLoginId;

	@Basic(optional = false)
    @NotNull
    @Column(name = "urls_url_id", nullable = false)
    private int urlsUrlId;

    public UsersHasUrlsPK() {
    }

    public UsersHasUrlsPK(int usersLoginId, int urlsUrlId) {
        this.usersLoginId = usersLoginId;
        this.urlsUrlId = urlsUrlId;
    }

    public int getUsersLoginId() {
        return usersLoginId;
    }

    public void setUsersLoginId(int usersLoginId) {
        this.usersLoginId = usersLoginId;
    }

    public int getUrlsUrlId() {
        return urlsUrlId;
    }

    public void setUrlsUrlId(int urlsUrlId) {
        this.urlsUrlId = urlsUrlId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usersLoginId;
        hash += (int) urlsUrlId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersHasUrlsPK)) {
            return false;
        }
        UsersHasUrlsPK other = (UsersHasUrlsPK) object;
        if (this.usersLoginId != other.usersLoginId) {
            return false;
        }
        if (this.urlsUrlId != other.urlsUrlId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.user.UsersHasUrlsPK[ usersLoginId=" + usersLoginId + ", urlsUrlId=" + urlsUrlId + " ]";
    }
    
}

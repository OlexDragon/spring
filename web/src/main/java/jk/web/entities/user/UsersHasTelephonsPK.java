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
public class UsersHasTelephonsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "users_login_id", nullable = false)
    private int usersLoginId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telephons_telephon_id", nullable = false)
    private int telephonsTelephonId;

    public UsersHasTelephonsPK() {
    }

    public UsersHasTelephonsPK(int usersLoginId, int telephonsTelephonId) {
        this.usersLoginId = usersLoginId;
        this.telephonsTelephonId = telephonsTelephonId;
    }

    public int getUsersLoginId() {
        return usersLoginId;
    }

    public void setUsersLoginId(int usersLoginId) {
        this.usersLoginId = usersLoginId;
    }

    public int getTelephonsTelephonId() {
        return telephonsTelephonId;
    }

    public void setTelephonsTelephonId(int telephonsTelephonId) {
        this.telephonsTelephonId = telephonsTelephonId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usersLoginId;
        hash += (int) telephonsTelephonId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersHasTelephonsPK)) {
            return false;
        }
        UsersHasTelephonsPK other = (UsersHasTelephonsPK) object;
        if (this.usersLoginId != other.usersLoginId) {
            return false;
        }
        if (this.telephonsTelephonId != other.telephonsTelephonId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.user.UsersHasTelephonsPK[ usersLoginId=" + usersLoginId + ", telephonsTelephonId=" + telephonsTelephonId + " ]";
    }
    
}

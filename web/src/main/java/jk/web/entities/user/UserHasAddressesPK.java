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
public class UserHasAddressesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "users_login_id", nullable = false)
    private int usersLoginId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "addresses_addsress_id", nullable = false)
    private int addressesAddsressId;

    public UserHasAddressesPK() {
    }

    public UserHasAddressesPK(int usersLoginId, int addressesAddsressId) {
        this.usersLoginId = usersLoginId;
        this.addressesAddsressId = addressesAddsressId;
    }

    public int getUsersLoginId() {
        return usersLoginId;
    }

    public void setUsersLoginId(int usersLoginId) {
        this.usersLoginId = usersLoginId;
    }

    public int getAddressesAddsressId() {
        return addressesAddsressId;
    }

    public void setAddressesAddsressId(int addressesAddsressId) {
        this.addressesAddsressId = addressesAddsressId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usersLoginId;
        hash += (int) addressesAddsressId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserHasAddressesPK)) {
            return false;
        }
        UserHasAddressesPK other = (UserHasAddressesPK) object;
        if (this.usersLoginId != other.usersLoginId) {
            return false;
        }
        if (this.addressesAddsressId != other.addressesAddsressId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.user.UsersHasAddressesPK[ usersLoginId=" + usersLoginId + ", addressesAddsressId=" + addressesAddsressId + " ]";
    }
    
}

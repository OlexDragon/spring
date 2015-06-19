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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jk.web.entities.AddressEntity;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "users_has_addresses", catalog = "jk", schema = "")
@XmlRootElement
public class UserHasAddresses implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected UserHasAddressesPK usersHasAddressesPK;

    @Basic(optional = false)
    @NotNull
    @Column(name = "address_status", nullable = false)
    private int addressStatus;

    @JoinColumn(name = "users_login_id", referencedColumnName = "login_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonBackReference
    private UserEntity userEntity;

    @JoinColumn(name = "addresses_addsress_id", referencedColumnName = "addsress_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonManagedReference
    private UserAddressEntity addressEntity;

    public UserHasAddresses() {
    }

    public UserHasAddresses(UserHasAddressesPK usersHasAddressesPK) {
        this.usersHasAddressesPK = usersHasAddressesPK;
    }

    public UserHasAddresses(UserHasAddressesPK usersHasAddressesPK, int addressStatus) {
        this.usersHasAddressesPK = usersHasAddressesPK;
        this.addressStatus = addressStatus;
    }

    public UserHasAddresses(int usersLoginId, int addressesAddsressId) {
        this.usersHasAddressesPK = new UserHasAddressesPK(usersLoginId, addressesAddsressId);
    }

    public UserHasAddressesPK getUsersHasAddressesPK() {
        return usersHasAddressesPK;
    }

    public void setUsersHasAddressesPK(UserHasAddressesPK usersHasAddressesPK) {
        this.usersHasAddressesPK = usersHasAddressesPK;
    }

    public int getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(int addressStatus) {
        this.addressStatus = addressStatus;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(UserAddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usersHasAddressesPK != null ? usersHasAddressesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserHasAddresses)) {
            return false;
        }
        UserHasAddresses other = (UserHasAddresses) object;
        if ((this.usersHasAddressesPK == null && other.usersHasAddressesPK != null) || (this.usersHasAddressesPK != null && !this.usersHasAddressesPK.equals(other.usersHasAddressesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.user.UsersHasAddresses[ usersHasAddressesPK=" + usersHasAddressesPK + " ]";
    }
    
}

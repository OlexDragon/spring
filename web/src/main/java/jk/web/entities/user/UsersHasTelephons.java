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

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "users_has_telephons", catalog = "jk", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersHasTelephons.findAll", query = "SELECT u FROM UsersHasTelephons u"),
    @NamedQuery(name = "UsersHasTelephons.findByUsersLoginId", query = "SELECT u FROM UsersHasTelephons u WHERE u.usersHasTelephonsPK.usersLoginId = :usersLoginId"),
    @NamedQuery(name = "UsersHasTelephons.findByTelephonsTelephonId", query = "SELECT u FROM UsersHasTelephons u WHERE u.usersHasTelephonsPK.telephonsTelephonId = :telephonsTelephonId"),
    @NamedQuery(name = "UsersHasTelephons.findByTelephonStatus", query = "SELECT u FROM UsersHasTelephons u WHERE u.telephonStatus = :telephonStatus")})
public class UsersHasTelephons implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected UsersHasTelephonsPK usersHasTelephonsPK;

    @Basic(optional = false)
    @NotNull
    @Column(name = "telephon_status", nullable = false)
    private int telephonStatus;

    @JoinColumn(name = "users_login_id", referencedColumnName = "login_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonBackReference
    private UserEntity userEntity;

    @JoinColumn(name = "telephons_telephon_id", referencedColumnName = "telephon_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserTelephonEntity telephonEntity;

    public UsersHasTelephons() {
    }

    public UsersHasTelephons(UsersHasTelephonsPK usersHasTelephonsPK) {
        this.usersHasTelephonsPK = usersHasTelephonsPK;
    }

    public UsersHasTelephons(UsersHasTelephonsPK usersHasTelephonsPK, int telephonStatus) {
        this.usersHasTelephonsPK = usersHasTelephonsPK;
        this.telephonStatus = telephonStatus;
    }

    public UsersHasTelephons(int usersLoginId, int telephonsTelephonId) {
        this.usersHasTelephonsPK = new UsersHasTelephonsPK(usersLoginId, telephonsTelephonId);
    }

    public UsersHasTelephonsPK getUsersHasTelephonsPK() {
        return usersHasTelephonsPK;
    }

    public void setUsersHasTelephonsPK(UsersHasTelephonsPK usersHasTelephonsPK) {
        this.usersHasTelephonsPK = usersHasTelephonsPK;
    }

    public int getTelephonStatus() {
        return telephonStatus;
    }

    public void setTelephonStatus(int telephonStatus) {
        this.telephonStatus = telephonStatus;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserTelephonEntity getTelephonEntity() {
        return telephonEntity;
    }

    public void setTelephonEntity(UserTelephonEntity telephonEntity) {
        this.telephonEntity = telephonEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usersHasTelephonsPK != null ? usersHasTelephonsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersHasTelephons)) {
            return false;
        }
        UsersHasTelephons other = (UsersHasTelephons) object;
        if ((this.usersHasTelephonsPK == null && other.usersHasTelephonsPK != null) || (this.usersHasTelephonsPK != null && !this.usersHasTelephonsPK.equals(other.usersHasTelephonsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.user.UsersHasTelephons[ usersHasTelephonsPK=" + usersHasTelephonsPK + " ]";
    }
    
}

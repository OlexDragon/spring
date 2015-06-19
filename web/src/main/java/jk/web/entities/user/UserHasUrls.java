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

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "users_has_urls", catalog = "jk", schema = "")
@XmlRootElement
public class UserHasUrls implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected UsersHasUrlsPK usersHasUrlsPK;

    @Basic(optional = false)
    @NotNull
    @Column(name = "url_status", nullable = false)
    private int urlStatus;

    @JoinColumn(name = "users_login_id", referencedColumnName = "login_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @JoinColumn(name = "urls_url_id", referencedColumnName = "url_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonBackReference
    private UserfUrlEntity urlEntity;

    public UserHasUrls() {
    }

    public UserHasUrls(UsersHasUrlsPK usersHasUrlsPK) {
        this.usersHasUrlsPK = usersHasUrlsPK;
    }

    public UserHasUrls(UsersHasUrlsPK usersHasUrlsPK, int urlStatus) {
        this.usersHasUrlsPK = usersHasUrlsPK;
        this.urlStatus = urlStatus;
    }

    public UserHasUrls(int usersLoginId, int urlsUrlId) {
        this.usersHasUrlsPK = new UsersHasUrlsPK(usersLoginId, urlsUrlId);
    }

    public UsersHasUrlsPK getUsersHasUrlsPK() {
        return usersHasUrlsPK;
    }

    public void setUsersHasUrlsPK(UsersHasUrlsPK usersHasUrlsPK) {
        this.usersHasUrlsPK = usersHasUrlsPK;
    }

    public int getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(int urlStatus) {
        this.urlStatus = urlStatus;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserfUrlEntity getUrlEntity() {
        return urlEntity;
    }

    public void setUrlEntity(UserfUrlEntity urlEntity) {
        this.urlEntity = urlEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usersHasUrlsPK != null ? usersHasUrlsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserHasUrls)) {
            return false;
        }
        UserHasUrls other = (UserHasUrls) object;
        if ((this.usersHasUrlsPK == null && other.usersHasUrlsPK != null) || (this.usersHasUrlsPK != null && !this.usersHasUrlsPK.equals(other.usersHasUrlsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.user.UsersHasUrls[ usersHasUrlsPK=" + usersHasUrlsPK + " ]";
    }
    
}

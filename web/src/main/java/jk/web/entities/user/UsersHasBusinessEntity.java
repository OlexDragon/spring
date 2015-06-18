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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import jk.web.entities.business.BusinessEntity;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "users_has_business", catalog = "jk", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersHasBusinessEntity.findAll", query = "SELECT u FROM UsersHasBusinessEntity u"),
    @NamedQuery(name = "UsersHasBusinessEntity.findByLoginId", query = "SELECT u FROM UsersHasBusinessEntity u WHERE u.usersHasBusinessEntityPK.loginId = :loginId"),
    @NamedQuery(name = "UsersHasBusinessEntity.findByBusinessId", query = "SELECT u FROM UsersHasBusinessEntity u WHERE u.usersHasBusinessEntityPK.businessId = :businessId"),
    @NamedQuery(name = "UsersHasBusinessEntity.findByStatus", query = "SELECT u FROM UsersHasBusinessEntity u WHERE u.status = :status")})
public class UsersHasBusinessEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected UsersHasBusinessEntityPK usersHasBusinessEntityPK;

    public enum BusinessStatus{
    	OWNER
    }
    @Basic(optional = false)
    @NotNull
    @Column(name = "status", nullable = false)
    private BusinessStatus status;

    @JoinColumn(name = "login_id", referencedColumnName = "login_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UserEntity userEntity;

    @JoinColumn(name = "business_id", referencedColumnName = "business_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private BusinessEntity businessEntity;

    public UsersHasBusinessEntity() {
    }
 
    public void setStatus(BusinessStatus status) {
        this.status = status;
    }
    
    public UsersHasBusinessEntity(UsersHasBusinessEntityPK usersHasBusinessEntityPK) {
        this.usersHasBusinessEntityPK = usersHasBusinessEntityPK;
    }

    public UsersHasBusinessEntity(UsersHasBusinessEntityPK usersHasBusinessEntityPK, BusinessStatus status) {
        this.usersHasBusinessEntityPK = usersHasBusinessEntityPK;
        this.status = status;
    }

    public UsersHasBusinessEntity(int loginId, int businessId) {
        this.usersHasBusinessEntityPK = new UsersHasBusinessEntityPK(loginId, businessId);
    }

    public UsersHasBusinessEntityPK getUsersHasBusinessEntityPK() {
        return usersHasBusinessEntityPK;
    }

    public void setUsersHasBusinessEntityPK(UsersHasBusinessEntityPK usersHasBusinessEntityPK) {
        this.usersHasBusinessEntityPK = usersHasBusinessEntityPK;
    }


    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usersHasBusinessEntityPK != null ? usersHasBusinessEntityPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersHasBusinessEntity)) {
            return false;
        }
        UsersHasBusinessEntity other = (UsersHasBusinessEntity) object;
        if ((this.usersHasBusinessEntityPK == null && other.usersHasBusinessEntityPK != null) || (this.usersHasBusinessEntityPK != null && !this.usersHasBusinessEntityPK.equals(other.usersHasBusinessEntityPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.UsersHasBusinessEntity[ usersHasBusinessEntityPK=" + usersHasBusinessEntityPK + " ]";
    }

    public BusinessStatus getStatus() {
        return status;
    }

}

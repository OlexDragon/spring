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
public class UsersHasBusinessEntityPK implements Serializable {
	private static final long serialVersionUID = 1550136832624625842L;

	@Basic(optional = false)
    @NotNull
    @Column(name = "login_id", nullable = false)
    private int loginId;

	@Basic(optional = false)
    @NotNull
    @Column(name = "business_id", nullable = false)
    private int businessId;

    public UsersHasBusinessEntityPK() {
    }

    public UsersHasBusinessEntityPK(int loginId, int businessId) {
        this.loginId = loginId;
        this.businessId = businessId;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) loginId;
        hash += (int) businessId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersHasBusinessEntityPK)) {
            return false;
        }
        UsersHasBusinessEntityPK other = (UsersHasBusinessEntityPK) object;
        if (this.loginId != other.loginId) {
            return false;
        }
        if (this.businessId != other.businessId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.UsersHasBusinessEntityPK[ loginId=" + loginId + ", businessId=" + businessId + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jk.web.entities.BlacklistEntity.BlackListType;

/**
 *
 * @author Alex
 */
@Embeddable
public class BlacklistEntityPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "blacklist_value")
    private String blacklistValue;

	@Basic(optional = false)
    @NotNull
    @Column(name = "blacklist_type")
    @Enumerated(EnumType.ORDINAL)
    private BlackListType blacklistType;

    public BlacklistEntityPK() {
    }

    public BlacklistEntityPK(String blacklistValue, BlackListType blacklistType) {
        this.blacklistValue = blacklistValue;
        this.blacklistType = blacklistType;
    }

    public String getBlacklistValue() {
        return blacklistValue;
    }

    public void setBlacklistValue(String blacklistValue) {
        this.blacklistValue = blacklistValue;
    }

    public BlackListType getBlacklistType() {
        return blacklistType;
    }

    public void setBlacklistType(BlackListType blacklistType) {
        this.blacklistType = blacklistType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (blacklistValue != null ? blacklistValue.hashCode() : 0);
        hash += blacklistType.ordinal();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BlacklistEntityPK)) {
            return false;
        }
        BlacklistEntityPK other = (BlacklistEntityPK) object;
        if ((this.blacklistValue == null && other.blacklistValue != null) || (this.blacklistValue != null && !this.blacklistValue.equals(other.blacklistValue))) {
            return false;
        }
        if (this.blacklistType != other.blacklistType) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.BlacklistEntityPK[ blacklistValue=" + blacklistValue + ", blacklistType=" + blacklistType + " ]";
    }
    
}

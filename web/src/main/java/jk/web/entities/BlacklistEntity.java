package jk.web.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "blacklist")
@XmlRootElement
public class BlacklistEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum BlackListType{
    	CONTAINS
    }

    @EmbeddedId
    protected BlacklistEntityPK blacklistEntityPK;

    public BlacklistEntity() {
    }

    public BlacklistEntity(BlacklistEntityPK blacklistEntityPK) {
        this.blacklistEntityPK = blacklistEntityPK;
    }

    public BlacklistEntityPK getBlacklistEntityPK() {
        return blacklistEntityPK;
    }

    public void setBlacklistEntityPK(BlacklistEntityPK blacklistEntityPK) {
        this.blacklistEntityPK = blacklistEntityPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (blacklistEntityPK != null ? blacklistEntityPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BlacklistEntity)) {
            return false;
        }
        BlacklistEntity other = (BlacklistEntity) object;
        if ((this.blacklistEntityPK == null && other.blacklistEntityPK != null) || (this.blacklistEntityPK != null && !this.blacklistEntityPK.equals(other.blacklistEntityPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.BlacklistEntity[ blacklistEntityPK=" + blacklistEntityPK + " ]";
    }
    
}

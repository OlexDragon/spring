/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.entities.user;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "workplaces")
@XmlRootElement
public class WorkplaceEntity implements Serializable {
    private static final long serialVersionUID = 1L;
 
    @EmbeddedId
    @JsonProperty("key")
    protected WorkplacesPK workplacesPK;

    public WorkplaceEntity() {
    }

    public WorkplaceEntity(WorkplacesPK workplacesPK) {
        this.workplacesPK = workplacesPK;
    }

    public WorkplaceEntity(Long loginsloginID, String workplace) {
        this.workplacesPK = new WorkplacesPK(loginsloginID, workplace);
    }

    public WorkplacesPK getWorkplacesPK() {
        return workplacesPK;
    }

    public void setWorkplacesPK(WorkplacesPK workplacesPK) {
        this.workplacesPK = workplacesPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workplacesPK != null ? workplacesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkplaceEntity)) {
            return false;
        }
        WorkplaceEntity other = (WorkplaceEntity) object;
        if ((this.workplacesPK == null && other.workplacesPK != null) || (this.workplacesPK != null && !this.workplacesPK.equals(other.workplacesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.user.entities.Workplaces[ workplacesPK=" + workplacesPK + " ]";
    }

}

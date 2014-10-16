/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.user.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Oleksandr Potomkin
 */
@Embeddable
public class WorkplacesPK implements Serializable {
	private static final long serialVersionUID = -1020924850004093413L;

	@Basic(optional = false)
    @NotNull
    @Column(name = "logins_loginID")
    private Long loginsloginID;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "workplace")
    private String workplace;

    public WorkplacesPK() {
    }

    public WorkplacesPK(Long loginsloginID, String workplace) {
        this.loginsloginID = loginsloginID;
        this.workplace = workplace;
    }

    public Long getLoginsloginID() {
        return loginsloginID;
    }

    public void setLoginsloginID(Long loginsloginID) {
        this.loginsloginID = loginsloginID;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    @Override
    public int hashCode() {
        int hash = loginsloginID!=null ? loginsloginID.hashCode() : 0;
        hash += (workplace != null ? workplace.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return object!=null ? object.hashCode()==hashCode() : false;
    }

    @Override
    public String toString() {
        return "jk.web.user.entities.WorkplacesPK[ loginsloginID=" + loginsloginID + ", workplace=" + workplace + " ]";
    }

}

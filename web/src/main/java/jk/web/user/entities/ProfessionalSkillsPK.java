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
public class ProfessionalSkillsPK implements Serializable {
	private static final long serialVersionUID = -270708884976950728L;

	@Basic(optional = false)
    @NotNull
    @Column(name = "logins_loginID")
    private Long loginsloginID;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "professional_skill")
    private String professionalSkill;

    public ProfessionalSkillsPK() {
    }

    public ProfessionalSkillsPK(Long loginsloginID, String professionalSkill) {
        this.loginsloginID = loginsloginID;
        this.professionalSkill = professionalSkill;
    }

    public Long getLoginsloginID() {
        return loginsloginID;
    }

    public void setLoginsloginID(Long loginsloginID) {
        this.loginsloginID = loginsloginID;
    }

    public String getProfessionalSkill() {
        return professionalSkill;
    }

    public void setProfessionalSkill(String professionalSkill) {
        this.professionalSkill = professionalSkill;
    }

    @Override
    public int hashCode() {
        int hash = loginsloginID!=null ? loginsloginID.hashCode() : 0;
        hash += (professionalSkill != null ? professionalSkill.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return object!=null ? object.hashCode()==hashCode() : false;
    }

    @Override
    public String toString() {
        return "jk.web.user.entities.ProfessionalSkillsPK[ loginsloginID=" + loginsloginID + ", professionalSkillscol=" + professionalSkill + " ]";
    }

}

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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Oleksandr Potomkin
 */
@Embeddable
public class ProfessionalSkillsPK implements Serializable {
	private static final long serialVersionUID = -270708884976950728L;

	@Basic(optional = false)
    @NotNull
    @Column(name = "login_id")
    @JsonProperty("login_id")
    private Long loginID;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "professional_skill")
	@JsonProperty("name")
    private String professionalSkill;

    public ProfessionalSkillsPK() {
    }

    public ProfessionalSkillsPK(Long loginID, String professionalSkill) {
        this.loginID = loginID;
        this.professionalSkill = professionalSkill;
    }

    public Long getloginID() {
        return loginID;
    }

    public void setloginID(Long loginID) {
        this.loginID = loginID;
    }

    public String getProfessionalSkill() {
        return professionalSkill;
    }

    public void setProfessionalSkill(String professionalSkill) {
        this.professionalSkill = professionalSkill;
    }

    @Override
    public int hashCode() {
        return (professionalSkill != null ? professionalSkill.hashCode() *31 : 0) + (loginID!=null ? loginID.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        return object!=null ? object.hashCode()==hashCode() : false;
    }

    @Override
    public String toString() {
        return "jk.web.user.entities.ProfessionalSkillsPK[ loginID=" + loginID + ", professionalSkillscol=" + professionalSkill + " ]";
    }

}

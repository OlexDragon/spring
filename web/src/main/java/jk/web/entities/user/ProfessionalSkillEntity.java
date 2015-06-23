/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.entities.user;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "professional_skills")
@XmlRootElement
public class ProfessionalSkillEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @JsonProperty("skill")
    protected ProfessionalSkillsPK key;

    @JoinColumn(name = "login_id", referencedColumnName = "login_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonBackReference
    private UserEntity userEntity;

    public ProfessionalSkillEntity() {
    }

    public ProfessionalSkillEntity(ProfessionalSkillsPK professionalSkillsPK) {
        this.key = professionalSkillsPK;
    }

    public ProfessionalSkillEntity(Long loginID, String professionalSkillStr) {
        this.key = new ProfessionalSkillsPK(loginID, professionalSkillStr);
    }

    public ProfessionalSkillsPK getKey() {
        return key;
    }

    public void setKeyK(ProfessionalSkillsPK professionalSkillsPK) {
        this.key = professionalSkillsPK;
    }

    public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ProfessionalSkillEntity ? object.hashCode() == hashCode() : false;
    }

    @Override
    public String toString() {
        return "jk.web.user.entities.ProfessionalSkills[ professionalSkillsPK=" + key + " ]";
    }

}

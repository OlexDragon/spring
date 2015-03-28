/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.entities.user;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "professional_skills")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProfessionalSkillEntity.findAll", query = "SELECT p FROM ProfessionalSkillEntity p"),
    @NamedQuery(name = "ProfessionalSkillEntity.findByLoginsloginID", query = "SELECT p FROM ProfessionalSkillEntity p WHERE p.professionalSkillsPK.loginsloginID = :loginsloginID"),
    @NamedQuery(name = "ProfessionalSkillEntity.findByProfessionalSkill", query = "SELECT p FROM ProfessionalSkillEntity p WHERE p.professionalSkillsPK.professionalSkill = :professionalSkill")})
public class ProfessionalSkillEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected ProfessionalSkillsPK professionalSkillsPK;

    public ProfessionalSkillEntity() {
    }

    public ProfessionalSkillEntity(ProfessionalSkillsPK professionalSkillsPK) {
        this.professionalSkillsPK = professionalSkillsPK;
    }

    public ProfessionalSkillEntity(Long loginsloginID, String professionalSkillscol) {
        this.professionalSkillsPK = new ProfessionalSkillsPK(loginsloginID, professionalSkillscol);
    }

    public ProfessionalSkillsPK getProfessionalSkillsPK() {
        return professionalSkillsPK;
    }

    public void setProfessionalSkillsPK(ProfessionalSkillsPK professionalSkillsPK) {
        this.professionalSkillsPK = professionalSkillsPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (professionalSkillsPK != null ? professionalSkillsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfessionalSkillEntity)) {
            return false;
        }
        ProfessionalSkillEntity other = (ProfessionalSkillEntity) object;
        if ((this.professionalSkillsPK == null && other.professionalSkillsPK != null) || (this.professionalSkillsPK != null && !this.professionalSkillsPK.equals(other.professionalSkillsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.user.entities.ProfessionalSkills[ professionalSkillsPK=" + professionalSkillsPK + " ]";
    }

}
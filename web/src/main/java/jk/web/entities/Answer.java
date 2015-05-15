/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "answer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Answer.findAll", query = "SELECT a FROM Answer a")})
public class Answer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "contact_us_id")
    private Integer contactUsId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "answer")
    private String answer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "answer_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date answerDate;

    public Answer() {
    }

    public Answer(Integer contactUsId) {
        this.contactUsId = contactUsId;
    }

    public Answer(Integer contactUsId, String answer, Date answerDate) {
        this.contactUsId = contactUsId;
        this.answer = answer;
        this.answerDate = answerDate;
    }

    public Integer getContactUsId() {
        return contactUsId;
    }

    public void setContactUsId(Integer contactUsId) {
        this.contactUsId = contactUsId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contactUsId != null ? contactUsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Answer)) {
            return false;
        }
        Answer other = (Answer) object;
        if ((this.contactUsId == null && other.contactUsId != null) || (this.contactUsId != null && !this.contactUsId.equals(other.contactUsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.Answer[ contactUsId=" + contactUsId + " ]";
    }
    
}

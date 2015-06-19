/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "contact_emails", catalog = "jk", schema = "")
public class ContactEmailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum EmailStatus{
    	TO_CONFIRM,
    	CONFIRMED,
    	ACTIVE,
    	DISABLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "email_id")
    private Long emailId;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "contact_email")
    private String email;

    @Column(name = "email_creation_date",insertable=false, updatable=false)
    private Timestamp emailCreationDate;

    public ContactEmailEntity() {
    }

    public ContactEmailEntity(Long emailId) {
        this.emailId = emailId;
    }

    public ContactEmailEntity(String email) {
        this.email = email;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getEmailCreationDate() {
        return emailCreationDate;
    }

    public void setEmailCreationDate(Timestamp emailCreationDate) {
        this.emailCreationDate = emailCreationDate;
    }

    @Override
    public int hashCode() {
        return emailId != null ? emailId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ContactEmailEntity ? object.hashCode()==hashCode() : false;
    }

	@Override
	public String toString() {
		return "/n\tContactEmailEntity [emailId=" + emailId + ", email=" + email
				+ ", emailCreationDate=" + emailCreationDate + "]";
	}
}

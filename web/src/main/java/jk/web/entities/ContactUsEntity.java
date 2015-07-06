package jk.web.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jk.web.entities.statistic.IpAddressEntity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "contact_us", catalog = "jk", schema = "")
public class ContactUsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "contact_us_id")
    private Long contactUsId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 245)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "subject")
    private String subject;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "message")
    private String message;

    @Column(name = "contact_date", insertable=false, updatable=false)
    private Timestamp contactDate;

    @Column(name = "answer_date", insertable=false)
    private Timestamp answerDate;

    @JoinColumn(name = "email_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JsonManagedReference
    private EmailEntity emailEntity;

    @JoinColumn(name = "ip_address_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private IpAddressEntity ipAddressEntity;

    @JoinColumn(name = "reference_number_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ReferenceNumberEntity referenceNumberEntity;

    public enum ContactUsStatus{
    	TO_ANSWER,
    	IN_PROCESS,
    	ANSWERED
    }
    @Column(name = "contact_status")//
    @Enumerated(EnumType.ORDINAL)
    private ContactUsStatus contactUsStatus;

    public ContactUsEntity() {
    }

    public ContactUsEntity(Long contactUsId) {
        this.contactUsId = contactUsId;
    }

    public ContactUsEntity(String name, String subject, String message, IpAddressEntity ipAddressEntity, ReferenceNumberEntity referenceNumberEntity, EmailEntity emailEntity, ContactUsStatus contactUsStatus) {
        this.name = name;
        this.subject = subject;
        this.message = message;
        this.contactUsStatus = contactUsStatus;
        this.referenceNumberEntity = referenceNumberEntity;
        this.emailEntity = emailEntity;
        this.ipAddressEntity = ipAddressEntity;
    }

    public Long getContactUsId() {
        return contactUsId;
    }

    public void setContactUsId(Long contactUsId) {
        this.contactUsId = contactUsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public Timestamp getContactDate() {
		return contactDate;
	}

    public void setContactDate(Timestamp contactDate) {
        this.contactDate = contactDate;
    }

    public EmailEntity getEmailEntity() {
        return emailEntity;
    }

    public void setEmailEntity(EmailEntity emailEntity) {
        this.emailEntity = emailEntity;
    }

    public IpAddressEntity getIpAddressEntity() {
        return ipAddressEntity;
    }

    public void setIpAddressEntity(IpAddressEntity ipAddressEntity) {
        this.ipAddressEntity = ipAddressEntity;
    }

    public ReferenceNumberEntity getReferenceNumberEntity() {
        return referenceNumberEntity;
    }

    public void setReferenceNumberEntity(ReferenceNumberEntity referenceNumberEntity) {
        this.referenceNumberEntity = referenceNumberEntity;
    }

    public ContactUsStatus getContactUsStatus() {
        return contactUsStatus;
    }

    public void setContactUsStatus(ContactUsStatus contactUsStatus) {
        this.contactUsStatus = contactUsStatus;
    }

	public Timestamp getAnswerDate() {
		return answerDate;
	}

    public void setAnswerDate(Timestamp contactDate) {
        this.answerDate = contactDate;
    }

    @Override
    public int hashCode() {
        return contactUsId != null ? contactUsId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ContactUsEntity ? object.hashCode()==hashCode() : false;
    }

    @Override
	public String toString() {
		return "\n\tContactUsEntity [\n\t\t"
				+ "contactUsId=" + contactUsId + ",\n\t\t"
						+ "name=" + name + ",\n\t\t"
								+ "subject=" + subject + ",\n\t\t"
										+ "message=" + message + ",\n\t\t"
												+ "contactDate=" + contactDate + ",\n\t\t"
														+ "contactEmailEntity=" + emailEntity + ",\n\t\t"
																+ "ipAddressEntity=" + ipAddressEntity + ",\n\t\t"
																		+ "referenceNumberEntity=" + referenceNumberEntity + ",\n\t\t"
																				+ "contactUsStatus=" + contactUsStatus + "]";
	}
}

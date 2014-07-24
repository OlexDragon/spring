package jk.web.user.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity(name="emails")
@Table(name="emails")
public class EMailEntity implements Serializable {
	private static final long serialVersionUID = -2050447855974732472L;

	public enum EMailStatus{
		TO_CONFIRM,
		CONFIRMED,
		NOT_ACTIVE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_emails")
	private Long id;

	@Basic(optional = false)
    @NotNull
    @Column(name = "id_users")
    private int idUsers;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
	@Column(name = "email", nullable = false, length = 145)
	private String eMail;

	@Basic(optional = false)
    @NotNull
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EMailStatus status;

    public Long getId() {
		return id;
	}
	public EMailEntity setId(Long id) {
		this.id = id;
		return this;
	}

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

	public String getEMail() {
		return eMail;
	}
	public EMailEntity setEMail(String eMail) {
		this.eMail = eMail;
		return this;
	}

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		return 31 + ((eMail == null) ? 0 : eMail.hashCode());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EMailEntity other = (EMailEntity) obj;
		return eMail != null ? eMail.equals(other.eMail) : other.eMail != null;
	}

    public EMailStatus getStatus() {
        return status;
    }

    public void setStatus(EMailStatus status) {
        this.status = status;
    }

    @Override
	public String toString() {
		return "EMailEntity [id=" + id + ", idUsers=" + idUsers + ", eMail=" + eMail + ", updateDate=" + updateDate + ", status=" + status + "]";
	}
}

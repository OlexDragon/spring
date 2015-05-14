package jk.web.entities.user;

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

@Entity(name="email")
@Table(name="emails", catalog="jk", schema = "")
public class EMailEntity implements Serializable {
	private static final long serialVersionUID = -2050447855974732472L;

	public enum EMailStatus{
		TO_CONFIRM,
		ACTIVE,
		NOT_ACTIVE,
		NOT_CONFIRMED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="emailID")
	private Long id;

	@Basic(optional = false)
    @NotNull
    @Column(name = "login_id")
    private Long userId;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
	@Column(name = "email", nullable = false, length = 145)
	private String eMail;

	@Basic
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

	@Basic
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Basic
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private EMailStatus status = EMailStatus.TO_CONFIRM;

    public Long getId() {
		return id;
	}
	public EMailEntity setId(Long id) {
		this.id = id;
		return this;
	}

    public Long getUserId() {
        return userId;
    }

    public EMailEntity setUserId(Long userId) {
        this.userId = userId;
        return this;
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

    public EMailEntity setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
        return this;
    }
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		return eMail == null ? 0 : eMail.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return obj!=null ? obj.hashCode()==hashCode() : false;
	}

    public EMailStatus getStatus() {
        return status;
    }

    public EMailEntity setStatus(EMailStatus status) {
        this.status = status;
        return this;
    }
	@Override
	public String toString() {
		return "EMailEntity [id=" + id + ", userId=" + userId + ", eMail=" + eMail + ", createDate=" + createDate + ", updateDate=" + updateDate + ", status=" + status + "]";
	}
}

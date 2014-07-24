package jk.web.user.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import jk.web.user.repository.LoginRepository.Permission;

@Entity(name="login")
@Table(name="logins")
public class LoginEntity implements Serializable{
	private static final long serialVersionUID = -884435162021696875L;

	@Id
	@Column(name="id_logins")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotNull
	@Column(name = "username", nullable = false, length = 65)
	private String username;

    @NotNull
	@Column(name = "password", nullable = false, length = 65)
	private String password;

	@Column(name = "permissions")
	private Permission permissions;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

	@Column(name = "last_accessed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessed;

    public LoginEntity() {
	}

    public LoginEntity(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Permission getPermissions() {
		return permissions;
	}

	public void setPermissions(Permission permissions) {
		if(permissions==null)
			this.permissions = Permission.NEW;
		else
			this.permissions = permissions;
	}

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(Date lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

	@Override
	public String toString() {
		return "LoginEntity [id=" + id + ", username=" + username + ", password=" + password + ", permissions=" + permissions + ", createdDate=" + createdDate + ", lastAccessed="
				+ lastAccessed + "]";
	}
}

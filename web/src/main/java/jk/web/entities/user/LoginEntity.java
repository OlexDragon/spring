package jk.web.entities.user;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.security.core.GrantedAuthority;

@Entity(name="login")
@Table(name="logins", catalog="jk", schema = "")
public class LoginEntity implements Serializable{
	private static final long serialVersionUID = -884435162021696875L;

	public enum Permission implements GrantedAuthority{
		ALL					(1	 ),
		DEFAULT				(1<<1),
		LOCKED				(1<<2),
		CREDENTIALS_EXPIRED	(1<<3),
		DISABLED			(1<<4),
		ACCOUNT_EXPIRED		(1<<5),
		NEW					(1<<6),
		BUSINESS			(1<<7),
		MANAGER				(1<<8),
		ADMIN				(1<<9);

		private long permission;

		private Permission(int permission){
			this.permission = permission;
		}

		@Override
		public String getAuthority() {
			return name();
		}

		public long toLong(){
			return permission;
		}

		public static Long toLong(Set<Permission> permissionsList){
			Long permissions;
			if(permissionsList==null || permissionsList.isEmpty())
				permissions = null;
			else{
				permissions = 0L;
				for(Permission p:permissionsList)
					permissions += Long.MAX_VALUE & p.permission;
			}

			return permissions;
		}

		public static Set<Permission> toPermissions(Long permissions){
			Set<Permission> set;

			if(permissions==null || permissions==0)
				set = null;
			else{
				set = new HashSet<Permission>();
				for(Permission p:Permission.values())
					if((p.permission&permissions)!=0)
						set.add(p);
			}

			return set;
		}

		public static boolean hasPermission(Long permissions, Permission permissionToCheckFor){
			return permissions!=null && (permissions&permissionToCheckFor.permission)>0;
		}

		public static Long addPermission(Long permissions, Permission permissionToAdd){
			return permissions!=null ? permissions|permissionToAdd.permission : permissionToAdd.permission;
		}

		public static Long removePermission(Long permissions, Permission permissionToAdd){
			return permissions!=null ? permissions&(-1l ^ permissionToAdd.permission) : null;
		}
	}

	@Id
	@Column(name="loginID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotNull
	@Column(name = "username", nullable = false, length = 65)
	private String username;

    @NotNull
	@Column(name = "password", nullable = false, length = 65)
	private String password;

	@Column(name = "permissions")
	private Long permissions;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

	@Column(name = "last_accessed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessed;

	//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "logins_loginID", referencedColumnName="loginID")
    @NotFound(action=NotFoundAction.IGNORE)
    @Cascade(value=CascadeType.ALL)
    private List<EMailEntity> emails;

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

	public LoginEntity setPassword(String password) {
		this.password = password;
		return this;
	}

	public Long getPermissions() {
		return permissions;
	}

	public void setPermissions(Long permissions) {
		if(permissions==null)
			this.permissions = Permission.NEW.toLong();
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

	public List<EMailEntity> getEmails() {
		return emails;
	}

	public void setEmails(List<EMailEntity> emails) {
		this.emails = emails;
	}

	@Override
	public String toString() {
		return "LoginEntity [id=" + id + ", username=" + username + ", password=" + password + ", permissions=" + permissions + ", createdDate=" + createdDate + ", lastAccessed="
				+ lastAccessed + ", emails=" + emails + "]";
	}
}

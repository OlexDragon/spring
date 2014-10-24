package jk.web.user.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import jk.web.user.User.Gender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author Oleksandr Potomkin
 */
@Entity(name="user")
@Table(name = "users", catalog = "jk", schema = "")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Transient
    private final Logger logger = LogManager.getLogger();

    @Id
    @Basic(optional = false)
    @Column(name = "logins_loginID", nullable = false)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name="title_id", updatable=false, insertable=false, nullable=true)
    private TitleEntity title;

    @Size(min = 1, max = 164)
    @Column(name = "first_name", length = 164)
    private String firstName;

    @Size(min = 1, max = 164)
    @Column(name = "last_name", length = 164)
    private String lastName;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "logins_loginID")
    @NotFound(action=NotFoundAction.IGNORE)
    @Cascade(value=CascadeType.ALL)
    private List<EMailEntity> emails;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "logins_loginID", referencedColumnName = "logins_loginID")
    @NotFound(action=NotFoundAction.IGNORE)
    @Cascade(value=CascadeType.ALL)
    private List<AddressEntity> addressEntities;

    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "logins_loginID", referencedColumnName = "logins_loginID")
    @NotFound(action=NotFoundAction.IGNORE)
    @Cascade(value=CascadeType.ALL)
    private List<ProfessionalSkillEntity> professionalSkills;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "logins_loginID", referencedColumnName = "logins_loginID")
    @NotFound(action=NotFoundAction.IGNORE)
    @Cascade(value=CascadeType.ALL)
    private List<WorkplaceEntity> workplaces;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "logins_loginID", referencedColumnName = "loginID", nullable = false, insertable = false, updatable = false)
    private LoginEntity loginEntity;

    public UserEntity() {
    }

    public UserEntity(Long id) {
        this.id = id;
    }

    public UserEntity(Long id, String firstName, String lastName, Timestamp birthday, Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
    }

    public Long getId() {
        return id!=null ? id : loginEntity!=null ? loginEntity.getId() : null;
    }

    public void setId(Long id) {
    	logger.entry(id);
        this.id = id;
    }

    public TitleEntity getTitle() {
		return title;
	}

	public void setTitle(TitleEntity title) {
		this.title = title;
	}

	public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
    	logger.entry(firstName);
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
    	logger.entry(lastName);
        this.lastName = lastName;
    }

    public List<EMailEntity> getEmails() {
        return emails;
    }

    public void setEmail(List<EMailEntity> emails) {
        this.emails = emails;
    }

    public List<AddressEntity> getAddressEntities() {
		return addressEntities;
	}

	public void setAddressEntities(List<AddressEntity> addressEntities) {
		this.addressEntities = addressEntities;
	}

	public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
    	logger.entry(birthday);
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
    	logger.entry(gender);
        this.gender = gender;
    }

    public LoginEntity getLoginEntity() {
        return loginEntity;
    }

    public UserEntity setLoginEntity(LoginEntity loginEntity) {
        this.loginEntity = loginEntity;
        return this;
    }

    @Override
    public int hashCode() {
         return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj!=null	? obj.hashCode()!=0 ? obj.hashCode()==hashCode()
        										: obj==this
        					: false;
    }

	public List<ProfessionalSkillEntity> getProfessionalSkills() {
		return professionalSkills;
	}

	public void setProfessionalSkills(List<ProfessionalSkillEntity> professionalSkills) {
		this.professionalSkills = professionalSkills;
	}

	public List<WorkplaceEntity> getWorkplaces() {
		return workplaces;
	}

	public void setWorkplaces(List<WorkplaceEntity> workplaces) {
		this.workplaces = workplaces;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", title=" + title + ", firstName=" + firstName + ", lastName=" + lastName + ", birthday=" + birthday + ", emails=" + emails
				+ ", addressEntities=" + addressEntities + ", gender=" + gender + ", professionalSkills=" + professionalSkills + ", workplaces=" + workplaces + ", loginEntity="
				+ loginEntity + "]";
	}
}

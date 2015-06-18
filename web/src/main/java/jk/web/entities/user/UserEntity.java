package jk.web.entities.user;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.xml.bind.annotation.XmlTransient;

import jk.web.user.User.Gender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Oleksandr Potomkin
 */
@Entity(name="user")
@Table(name = "users", catalog = "jk", schema = "")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum ActivityType{
    	NEW_USER
    }

    @Transient
    private final Logger logger = LogManager.getLogger();

    @Id
    @Basic(optional = false)
    @Column(name = "login_id", nullable = false)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name="title_id", updatable=false, insertable=false, nullable=true)
    @JsonProperty("title")
    private TitleEntity titleEntity;

    @Size(min = 1, max = 164)
    @Column(name = "first_name", length = 164)
    private String firstName;

    @Size(min = 1, max = 164)
    @Column(name = "last_name", length = 164)
    private String lastName;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "login_id")
    @NotFound(action=NotFoundAction.IGNORE)
    @Cascade(value=CascadeType.ALL)
    private List<ProfessionalSkillEntity> professionalSkills;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "login_id")
    @NotFound(action=NotFoundAction.IGNORE)
    @Cascade(value=CascadeType.ALL)
    private List<WorkplaceEntity> workplaces;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "login_id", nullable = false, insertable = false, updatable = false)
    @JsonProperty("login")
    private LoginEntity loginEntity;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "userEntity")
    private List<UsersHasBusinessEntity> usersHasBusinessEntityList;

    @Column(name = "gender")
    private Gender gender;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<UserHasAddresses> usersHasAddressesList;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<UsersHasTelephons> usersHasTelephonsList;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<UserHasUrls> usersHasUrlsList;

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

    public TitleEntity getTitleEntity() {
		return titleEntity;
	}

	public void setTitleEntity(TitleEntity titleEntity) {
		logger.entry(titleEntity);
		this.titleEntity = titleEntity;
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

	public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
    	logger.entry(birthday);
        this.birthday = birthday;
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

    @XmlTransient
    public List<UsersHasBusinessEntity> getUsersHasBusinessEntityList() {
        return usersHasBusinessEntityList;
    }

    public void setUsersHasBusinessEntityList(List<UsersHasBusinessEntity> usersHasBusinessEntityList) {
        this.usersHasBusinessEntityList = usersHasBusinessEntityList;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @XmlTransient
    public List<UserHasAddresses> getUsersHasAddressesList() {
        return usersHasAddressesList;
    }

    public void setUsersHasAddressesList(List<UserHasAddresses> usersHasAddressesList) {
        this.usersHasAddressesList = usersHasAddressesList;
    }

    @XmlTransient
    public List<UsersHasTelephons> getUsersHasTelephonsList() {
        return usersHasTelephonsList;
    }

    public void setUsersHasTelephonsList(List<UsersHasTelephons> usersHasTelephonsList) {
        this.usersHasTelephonsList = usersHasTelephonsList;
    }

    @XmlTransient
    public List<UserHasUrls> getUsersHasUrlsList() {
        return usersHasUrlsList;
    }

    public void setUsersHasUrlsList(List<UserHasUrls> usersHasUrlsList) {
        this.usersHasUrlsList = usersHasUrlsList;
    }

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", titleEntity=" + titleEntity + ", firstName=" + firstName + ", lastName=" + lastName + ", birthday=" + birthday
//				+ ", addressEntities=" + addressEntityList + ", gender=" + gender + ", professionalSkills=" + professionalSkills + ", workplaces=" + workplaces + ", loginEntity="
				+ loginEntity + "]";
	}
}

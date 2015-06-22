package jk.web.entities.user;

import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import jk.web.user.User.Gender;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Oleksandr Potomkin
 */
@Entity(name="user")
@Table(name = "users", catalog = "jk", schema = "")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 6310832069334467075L;

	public enum ActivityType{
    	NEW_USER
    }

    @Id
    @Basic(optional = false)
    @Column(name = "login_id", nullable = false)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name="title_id", nullable=true)
    @JsonProperty("title")
    @JsonManagedReference
    private TitleEntity titleEntity;

    @Size(min = 1, max = 164)
    @Column(name = "first_name", length = 164)
    private String firstName;

    @Size(min = 1, max = 164)
    @Column(name = "last_name", length = 164)
    private String lastName;

    @Column(name = "birthday")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp birthday;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "login_id")
    @NotFound(action=NotFoundAction.IGNORE)
    @Cascade(value=CascadeType.ALL)
    @JsonProperty("skills")
    private List<ProfessionalSkillEntity> professionalSkills;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "login_id")
    @NotFound(action=NotFoundAction.IGNORE)
    @Cascade(value=CascadeType.ALL)
    private List<WorkplaceEntity> workplaces;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "login_id", nullable = false, insertable = false, updatable = false)
    @JsonProperty("login")
    @JsonManagedReference
    private LoginEntity loginEntity;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "userEntity")
    @JsonProperty("businesses")
    @JsonManagedReference
    private List<UsersHasBusinesses> hasBusinesses;

    @Column(name = "gender")
    private Gender gender;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "userEntity", fetch = FetchType.LAZY)
    @JsonProperty("addresses")
    @JsonManagedReference
    private List<UserHasAddresses> hasAddresses;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "userEntity", fetch = FetchType.LAZY)
    @JsonProperty("telephons")
    @JsonManagedReference
    private List<UsersHasTelephons> hasTelephons;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "userEntity", fetch = FetchType.LAZY)
    @JsonProperty("urls")
    @JsonManagedReference
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
        this.id = id;
    }

    public TitleEntity getTitleEntity() {
		return titleEntity;
	}

	public void setTitleEntity(TitleEntity titleEntity) {
		this.titleEntity = titleEntity;
	}

	public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
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
    public List<UsersHasBusinesses> getHasBusinesses() {
        return hasBusinesses;
    }

    public void seHasBusinesses(List<UsersHasBusinesses> usersHasBusinessEntityList) {
        this.hasBusinesses = usersHasBusinessEntityList;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @XmlTransient
    public List<UserHasAddresses> getHasAddresses() {
        return hasAddresses;
    }

    public void setHasAddresses(List<UserHasAddresses> usersHasAddressesList) {
        this.hasAddresses = usersHasAddressesList;
    }

    @XmlTransient
    public List<UsersHasTelephons> getHasTelephons() {
        return hasTelephons;
    }

    public void setHasTelephons(List<UsersHasTelephons> usersHasTelephonsList) {
        this.hasTelephons = usersHasTelephonsList;
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
		return "\n\tUserEntity [id=" + id + ", titleEntity=" + titleEntity + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", birthday=" + birthday + ", professionalSkills="
				+ professionalSkills + ", workplaces=" + workplaces + ", loginEntity=" + loginEntity + ", hasBusinesses=" + hasBusinesses
				+ ", hasAddresses=" + hasAddresses + ", hasTelephons=" + hasTelephons + ", usersHasUrlsList=" + usersHasUrlsList + "]";
	}
}

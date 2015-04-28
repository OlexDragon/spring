package jk.web.entities.user;

import java.io.Serializable;

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
import javax.xml.bind.annotation.XmlRootElement;

import jk.web.entities.user.UserEntity.ActivityType;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "activity")
@XmlRootElement
public class ActivityEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "activity_id")
    private Integer activityId;

	@Basic(optional = false)
    @NotNull
    @Column(name = "login_id")
    private long userid;

	@Basic(optional = false)
    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private ActivityType activityType;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "login_id", nullable = false, insertable = false, updatable = false)
    private UserEntity userEntity;

    public ActivityEntity() {
    }

    public ActivityEntity(Integer activityId) {
        this.activityId = activityId;
    }

    public ActivityEntity(Integer activityId, ActivityType type) {
        this.activityId = activityId;
        this.activityType = type;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }


    public UserEntity getUserEntity() {
		return userEntity;
	}

	public ActivityEntity setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
		return this;
	}

    public ActivityType getType() {
        return activityType;
    }

    public ActivityEntity setType(ActivityType newUser) {
        this.activityType = newUser;
        return this;
    }

	public long getUserid() {
		return userid;
	}

	public ActivityEntity setUserid(long userid) {
		this.userid = userid;
		return this;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (activityId != null ? activityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActivityEntity)) {
            return false;
        }
        ActivityEntity other = (ActivityEntity) object;
        if ((this.activityId == null && other.activityId != null) || (this.activityId != null && !this.activityId.equals(other.activityId))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "ActivityEntity [activityId=" + activityId + ", userid=" + userid + ", activityType=" + activityType + ", userEntity=" + userEntity + "]";
	}
}

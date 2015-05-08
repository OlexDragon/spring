/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "background_imgs")
@XmlRootElement
public class BackgroundImgEntity implements Serializable, Comparable<BackgroundImgEntity> {
    private static final long serialVersionUID = 1L;

    public enum BackgroundImgStatus{
    	NOT_USE,
    	USE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "background_img_id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 245)
    @Column(name = "path")
    private String path;

    @Basic(optional = false)
    @NotNull
    @Column(name = "start_to_show_at")
    private Time startToShowAt;

    @Enumerated(EnumType.ORDINAL)
     private BackgroundImgStatus status;

    public BackgroundImgEntity() {
    }

    public BackgroundImgEntity(Long backgroundImgId) {
        this.id = backgroundImgId;
    }

    public BackgroundImgEntity(Long backgroundImgId, String path, Time startToShowAt, BackgroundImgStatus status) {
        id = backgroundImgId;
        this.path = path;
        this.startToShowAt = startToShowAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long backgroundImgId) {
        id = backgroundImgId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Time getStartToShowAt() {
        return startToShowAt;
    }

    public void setStartToShowAt(Time startToShowAt) {
        this.startToShowAt = startToShowAt;
    }

    public BackgroundImgStatus getStatus() {
        return status;
    }

    public void setStatus(BackgroundImgStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BackgroundImgEntity)) {
            return false;
        }
        BackgroundImgEntity other = (BackgroundImgEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

	@Override
	public int compareTo(BackgroundImgEntity backgroundImgEntity) {
		int result;
		if(backgroundImgEntity!=null && backgroundImgEntity.getStartToShowAt()!=null){
			if(getStartToShowAt()!=null)
				result = getStartToShowAt().compareTo(backgroundImgEntity.getStartToShowAt());
			else
				result = 1;
		}else
			result = -1;
		return result;
	}

	@Override
	public String toString() {
		return "\n\tBackgroundImgEntity [\n\t\t"
				+ "id=" + id + ",\n\t\t"
				+ "path=" + path + ",\n\t\t"
				+ "startToShowAt=" + startToShowAt + ",\n\t"
				+ "status=" + status
				+ "]";
	}
}

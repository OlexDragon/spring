/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.entities.user;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "image_details", catalog = "jk", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImageDetailsEntity.findAll", query = "SELECT i FROM ImageDetailsEntity i"),
    @NamedQuery(name = "ImageDetailsEntity.findByFileID", query = "SELECT i FROM ImageDetailsEntity i WHERE i.fileID = :fileID"),
    @NamedQuery(name = "ImageDetailsEntity.findByHight", query = "SELECT i FROM ImageDetailsEntity i WHERE i.hight = :hight"),
    @NamedQuery(name = "ImageDetailsEntity.findByWidth", query = "SELECT i FROM ImageDetailsEntity i WHERE i.width = :width")})
public class ImageDetailsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum ImageMaxSize{
    	ORIGINAL,
    	SMALL,
    	MEDIUM,
    	LARGE
    }
  
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "files_fileID")
    private Long fileID;

    @Basic(optional = false)
    @NotNull
    @Column(name = "hight")
    private int hight;

    @Basic(optional = false)
    @NotNull
    @Column(name = "width")
    private int width;

    @Column(name = "max_size")
    @Enumerated(EnumType.ORDINAL)
    private ImageMaxSize imageMaxSize;

    public ImageDetailsEntity() {
    }

    public ImageDetailsEntity(Long fileID) {
        this.fileID = fileID;
    }

    public ImageDetailsEntity(Long fileID, int hight, int width) {
        this.fileID = fileID;
        this.hight = hight;
        this.width = width;
    }

    public Long getFileID() {
        return fileID;
    }

    public void setFileID(Long fileID) {
        this.fileID = fileID;
    }

    public int getHight() {
        return hight;
    }

    public void setHight(int hight) {
        this.hight = hight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

	public ImageMaxSize getImageMaxSize() {
		return imageMaxSize;
	}

	public void setImageMaxSize(ImageMaxSize imageMaxSize) {
		this.imageMaxSize = imageMaxSize;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fileID != null ? fileID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImageDetailsEntity)) {
            return false;
        }
        ImageDetailsEntity other = (ImageDetailsEntity) object;
        if ((this.fileID == null && other.fileID != null) || (this.fileID != null && !this.fileID.equals(other.fileID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.user.entities.ImageDetailsEntity[ fileID=" + fileID + " ]";
    }
}

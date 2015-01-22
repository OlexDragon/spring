/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.user.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "files", catalog = "jk", schema = "")
@XmlRootElement
public class FileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fileID")
	private Long fileID;

	@Basic(optional = false)
	@Column(name = "logins_loginID", nullable = false)
	private Long userID;

	@Size(max = 145)
	@NotNull
	@Column(name = "file_name")
	private String fileName;

	@Size(max = 145)
	@Column(name = "file_extension")
	private String fileExtension;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "content_type")
	private String contentType;

	@Basic(optional = false)
	@NotNull
	@Column(name = "size")
	private Long size;

	@Basic
	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Basic
	@Column(name = "show_to_all", columnDefinition = "BIT", length = 1)
	private boolean showToAll;

    @OneToOne(fetch=FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "fileID", referencedColumnName = "files_fileID")
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	private ImageDetailsEntity imageDetailsEntity;

	@OneToOne(fetch=FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "fileID", referencedColumnName = "files_fileID")
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	private FileDescriptionsEntity fileDescriptionsEntity;

	public FileEntity() {
	}

	public FileEntity(Long fileID) {
		this.fileID = fileID;
	}

	public FileEntity(Long fileID, String extension, Long size, Date date) {
		this.fileID = fileID;
		this.contentType = extension;
		this.size = size;
		this.date = date;
	}

	public Long getFileID() {
		return fileID;
	}

	public void setFileID(Long fileID) {
		this.fileID = fileID;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ImageDetailsEntity getImageDetailsEntity() {
		return imageDetailsEntity;
	}

	public void setImageDetailsEntity(ImageDetailsEntity imageDetailsEntity) {
		this.imageDetailsEntity = imageDetailsEntity;
	}

	public FileDescriptionsEntity getFileDescriptionsEntity() {
		return fileDescriptionsEntity;
	}

	public void setFileDescriptionsEntity(FileDescriptionsEntity fileDescriptionsEntity) {
		this.fileDescriptionsEntity = fileDescriptionsEntity;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public boolean isShowToAll() {
		return showToAll;
	}

	public void setShowToAll(boolean showToAll) {
		this.showToAll = showToAll;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (fileID != null ? fileID.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof FileEntity)) {
			return false;
		}
		FileEntity other = (FileEntity) object;
		if ((this.fileID == null && other.fileID != null) || (this.fileID != null && !this.fileID.equals(other.fileID))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "FileEntity [fileID=" + fileID + ", userID=" + userID + ", fileName=" + fileName + ", contentType=" + contentType + ", size=" + size + ", date=" + date
				+ ", imageDetailsEntity=" + imageDetailsEntity + ", fileDescriptionsEntity=" + fileDescriptionsEntity + "]";
	}
}

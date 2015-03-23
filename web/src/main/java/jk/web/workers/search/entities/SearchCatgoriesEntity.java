/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.workers.search.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Oleksandr Potomkin
 */

@Entity
@Table(name = "search_catgories", catalog = "jk", schema = "", uniqueConstraints = { @UniqueConstraint(columnNames = { "category_name" }) })
@XmlRootElement
public class SearchCatgoriesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "category_id", nullable = false)
	private Long categoryId;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 145)
	@Column(name = "category_name", nullable = false, length = 145)
	private String categoryName;

	public SearchCatgoriesEntity() {
	}

	public SearchCatgoriesEntity(Long categoryId) {
		this.categoryId = categoryId;
	}

	public SearchCatgoriesEntity(Long categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (categoryId != null ? categoryId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof SearchCatgoriesEntity)) {
			return false;
		}
		SearchCatgoriesEntity other = (SearchCatgoriesEntity) object;
		if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "\n\tSearchCatgoriesEntity [\n\t\tcategoryId=" + categoryId + ",\n\t\tcategoryName=" + categoryName + "]";
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.business;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import jk.web.entities.UrlEntity;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "urls", catalog = "jk", schema = "")
public class BusinessUrlEntity extends UrlEntity{
	private static final long serialVersionUID = -5839244918842612692L;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "urlEntity", fetch = FetchType.LAZY)
    private List<BusinessHasUrls> businessHasUrlsList;

    public BusinessUrlEntity() {
	}

    public BusinessUrlEntity(String url) {
		super(url);
	}

	@XmlTransient
    public List<BusinessHasUrls> getBusinessHasUrlsList() {
        return businessHasUrlsList;
    }

    public void setBusinessHasUrlsList(List<BusinessHasUrls> businessHasUrlsList) {
        this.businessHasUrlsList = businessHasUrlsList;
    }
}

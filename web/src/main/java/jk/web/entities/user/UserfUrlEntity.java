/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class UserfUrlEntity extends UrlEntity{
	private static final long serialVersionUID = -8513851370907311199L;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "urlEntity", fetch = FetchType.LAZY)
    private List<UserHasUrls> usersHasUrlsCollection;

    @XmlTransient
    public List<UserHasUrls> getUsersHasUrlsCollection() {
        return usersHasUrlsCollection;
    }

    public void setUsersHasUrlsCollection(List<UserHasUrls> usersHasUrlsCollection) {
        this.usersHasUrlsCollection = usersHasUrlsCollection;
    }
}

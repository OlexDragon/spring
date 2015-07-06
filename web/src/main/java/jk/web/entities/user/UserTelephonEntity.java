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
import javax.xml.bind.annotation.XmlTransient;

import jk.web.entities.TelephonEntity;

/**
 *
 * @author Oleksandr
 */
@Entity
public class UserTelephonEntity extends TelephonEntity {
	private static final long serialVersionUID = -8126154621541838128L;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "telephonEntity", fetch = FetchType.LAZY)
    private List<UsersHasTelephons> usersHasTelephonsList;

    @XmlTransient
    public List<UsersHasTelephons> getUsersHasTelephonsList() {
        return usersHasTelephonsList;
    }

    public void setUsersHasTelephonsList(List<UsersHasTelephons> usersHasTelephonsList) {
        this.usersHasTelephonsList = usersHasTelephonsList;
    }
    
}

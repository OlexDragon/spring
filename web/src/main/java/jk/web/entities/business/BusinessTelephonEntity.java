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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import jk.web.entities.TelephonEntity;

/**
 *
 * @author Oleksandr
 */
@Entity
@Table(name = "telephons", catalog = "jk", schema = "")
public class BusinessTelephonEntity extends TelephonEntity{
	private static final long serialVersionUID = -8113001601500593890L;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "telephonEntity", fetch = FetchType.LAZY)
    private List<BusinessHasTelephons> businessHasTelephonsList;

    public BusinessTelephonEntity() {
	}

    public BusinessTelephonEntity(String telephon) {
		super(telephon);
	}

    @XmlTransient
    public List<BusinessHasTelephons> getBusinessHasTelephonsList() {
        return businessHasTelephonsList;
    }

    public void setBusinessHasTelephonsList(List<BusinessHasTelephons> businessHasTelephonsList) {
        this.businessHasTelephonsList = businessHasTelephonsList;
    }
}

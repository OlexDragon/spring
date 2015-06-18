package jk.web.entities.business;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import jk.web.entities.AddressEntity;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "addresses", catalog = "", schema = "jk")
public class BusinessAddressEntity extends AddressEntity {
	private static final long serialVersionUID = 921268631106621453L;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "addressEntity", fetch = FetchType.LAZY)
    private List<BusinessHasAddresses> businessHasAddressesList;

	public List<BusinessHasAddresses> getBusinessHasAddressesList() {
		return businessHasAddressesList;
	}

	public void setBusinessHasAddressesList(
			List<BusinessHasAddresses> businessHasAddressesList) {
		this.businessHasAddressesList = businessHasAddressesList;
	}
}

package jk.web.entities.user;

import java.util.List;
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
public class UserAddressEntity extends AddressEntity{
	private static final long serialVersionUID = -1237438634242283596L;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "addressEntity", fetch = FetchType.LAZY)
    private List<UserHasAddresses> userHasAddressesCollection;

	public List<UserHasAddresses> getUserHasAddressesList() {
		return userHasAddressesCollection;
	}

	public void setUsersHasAddressesList( List<UserHasAddresses> userHasAddressesCollection) {
		this.userHasAddressesCollection = userHasAddressesCollection;
	}
}

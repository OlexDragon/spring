package jk.web.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class ContactUsEmailEntity extends ContactEmailEntity{
	private static final long serialVersionUID = -8327593949637569580L;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contactEmailEntity", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<ContactUsEntity> contactUsList;

    public List<ContactUsEntity> getContactUsList() {
        return contactUsList;
    }

    public void setContactUsList(List<ContactUsEntity> contactUsList) {
        this.contactUsList = contactUsList;
    }
}

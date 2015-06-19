package jk.web.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "contact_emails", catalog = "jk", schema = "")
public class ContactUsEmailEntity extends EmailEntity{
	private static final long serialVersionUID = -8327593949637569580L;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contactUsEmailEntity", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<ContactUsEntity> contactUsEntityList;

    public List<ContactUsEntity> getContactUsEntityList() {
        return contactUsEntityList;
    }

    public void setContactUsEntityList(List<ContactUsEntity> contactUsList) {
        this.contactUsEntityList = contactUsList;
    }
}

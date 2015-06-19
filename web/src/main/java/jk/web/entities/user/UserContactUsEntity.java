package jk.web.entities.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jk.web.entities.ContactUsEntity;
import jk.web.entities.ReferenceNumberEntity;
import jk.web.entities.statistic.IpAddressEntity;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "contact_us", catalog = "jk", schema = "")
public class UserContactUsEntity extends ContactUsEntity {
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "reference_number_id", referencedColumnName="login_id", insertable=false, updatable=false)
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private UserEntity userEntity;

    public UserContactUsEntity() {
    }

    public UserContactUsEntity(Long contactUsId) {
        super(contactUsId);
    }

    public UserContactUsEntity(String name, String subject, String message, IpAddressEntity ipAddressEntity, ReferenceNumberEntity referenceNumberEntity, UserEmailEntity contactEmailEntity, ContactUsStatus contactStatus) {
//    	super(name, subject, message, ipAddressEntity, referenceNumberEntity, contactEmailEntity, contactStatus);
     }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntityEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.user.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "social", catalog = "jk", schema = "")
@XmlRootElement
public class SocialEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "user_key", nullable = false, length = 45)
    private String userKey;
    @Size(max = 145)
    @Column(name = "username", length = 145)
    private String username;
    @Size(max = 145)
    @Column(name = "first_name", length = 145)
    private String firstName;
    @Size(max = 145)
    @Column(name = "last_name", length = 145)
    private String lastName;
    @Size(max = 245)
    @Column(name = "image_url", length = 245)
    private String imageUrl;
    @Size(max = 245)
    @Column(name = "profile_url", length = 245)
    private String profileUrl;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 145)
    @Column(name = "email", length = 145)
    private String email;

    public SocialEntity() {
    }

    public SocialEntity(String userKey) {
        this.userKey = userKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userKey != null ? userKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SocialEntity)) {
            return false;
        }
        SocialEntity other = (SocialEntity) object;
        if ((this.userKey == null && other.userKey != null) || (this.userKey != null && !this.userKey.equals(other.userKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.user.entities.Social[ userKey=" + userKey + " ]";
    }

}

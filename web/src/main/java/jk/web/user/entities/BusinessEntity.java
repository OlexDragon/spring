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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "business", catalog = "jk", schema = "")
@XmlRootElement
public class BusinessEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "logins_loginID", nullable = false)
    private Long userID;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 145)
    @Column(name = "company_name", nullable = false, length = 145)
    private String companyName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 245)
    @Column(name = "site_address", nullable = false, length = 245)
    private String siteAddress;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "country_of_activity", nullable = false, length = 3)
    private String countryOfActivity;

    @Size(max = 250)
    @Column(name = "post", length = 250)
    private String post;

    @Size(max = 145)
    @Column(name = "_condition", length = 145)
    private String condition;

    @Column(name = "VAT_number")
    private Long vATnumber;

    public BusinessEntity() {
    }

    public BusinessEntity(Long loginsloginID) {
        this.userID = loginsloginID;
    }

    public BusinessEntity(Long loginsloginID, String companyName, String siteAddress, String countryOfActivity) {
        this.userID = loginsloginID;
        this.companyName = companyName;
        this.siteAddress = siteAddress;
        this.countryOfActivity = countryOfActivity;
    }

    public Long getLoginsloginID() {
        return userID;
    }

    public BusinessEntity setUserID(Long userID) {
        this.userID = userID;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getCountryOfActivity() {
        return countryOfActivity;
    }

    public void setCountryOfActivity(String countryOfActivity) {
        this.countryOfActivity = countryOfActivity;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Long getVATnumber() {
        return vATnumber;
    }

    public void setVATnumber(Long vATnumber) {
        this.vATnumber = vATnumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessEntity)) {
            return false;
        }
        BusinessEntity other = (BusinessEntity) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
	public String toString() {
		return "BusinessEntity [userID=" + userID + ", companyName=" + companyName + ", siteAddress=" + siteAddress + ", countryOfActivity=" + countryOfActivity + ", post=" + post
				+ ", condition=" + condition + ", vATnumber=" + vATnumber + "]";
	}

}

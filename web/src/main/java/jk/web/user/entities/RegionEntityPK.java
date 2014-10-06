/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.user.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Oleksandr Potomkin
 */
@Embeddable
public class RegionEntityPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "region_code")
    private String regionCode;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "country_code")
    private String countryCode;

    public RegionEntityPK() {
    }

    public RegionEntityPK(String regionCode, String countryCode) {
        this.regionCode = regionCode;
        this.countryCode = countryCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regionCode != null ? regionCode.hashCode() : 0);
        hash += (countryCode != null ? countryCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegionEntityPK)) {
            return false;
        }
        RegionEntityPK other = (RegionEntityPK) object;
        if ((this.regionCode == null && other.regionCode != null) || (this.regionCode != null && !this.regionCode.equals(other.regionCode))) {
            return false;
        }
        if ((this.countryCode == null && other.countryCode != null) || (this.countryCode != null && !this.countryCode.equals(other.countryCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.user.entities.RegionEntityPK[ regionCode=" + regionCode + ", countryCode=" + countryCode + " ]";
    }

}

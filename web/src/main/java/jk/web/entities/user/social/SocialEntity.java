/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jk.web.entities.user.social;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Oleksandr Potomkin
 */
@Entity
@Table(name = "social")
@XmlRootElement
public class SocialEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SocialEntityPK socialEntityPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rank")
    private int rank;
    @Size(max = 255)
    @Column(name = "displayName")
    private String displayName;
    @Size(max = 512)
    @Column(name = "profileUrl")
    private String profileUrl;
    @Size(max = 512)
    @Column(name = "imageUrl")
    private String imageUrl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "accessToken")
    private String accessToken;
    @Size(max = 255)
    @Column(name = "secret")
    private String secret;
    @Size(max = 255)
    @Column(name = "refreshToken")
    private String refreshToken;
    @Column(name = "expireTime")
    private BigInteger expireTime;

    public SocialEntity() {
    }

    public SocialEntity(SocialEntityPK socialEntityPK) {
        this.socialEntityPK = socialEntityPK;
    }

    public SocialEntity(SocialEntityPK socialEntityPK, int rank, String accessToken) {
        this.socialEntityPK = socialEntityPK;
        this.rank = rank;
        this.accessToken = accessToken;
    }

    public SocialEntity(String userId, String providerId, String providerUserId) {
        this.socialEntityPK = new SocialEntityPK(userId, providerId, providerUserId);
    }

    public SocialEntityPK getSocialEntityPK() {
        return socialEntityPK;
    }

    public void setSocialEntityPK(SocialEntityPK socialEntityPK) {
        this.socialEntityPK = socialEntityPK;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public BigInteger getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(BigInteger expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (socialEntityPK != null ? socialEntityPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SocialEntity)) {
            return false;
        }
        SocialEntity other = (SocialEntity) object;
        if ((this.socialEntityPK == null && other.socialEntityPK != null) || (this.socialEntityPK != null && !this.socialEntityPK.equals(other.socialEntityPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.SocialEntity[ socialEntityPK=" + socialEntityPK + " ]";
    }

}

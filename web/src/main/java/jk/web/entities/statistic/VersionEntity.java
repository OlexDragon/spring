/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.statistic;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "versions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VersionEntity.findAll", query = "SELECT v FROM VersionEntity v"),
    @NamedQuery(name = "VersionEntity.findByVersionId", query = "SELECT v FROM VersionEntity v WHERE v.versionId = :versionId"),
    @NamedQuery(name = "VersionEntity.findByVersion", query = "SELECT v FROM VersionEntity v WHERE v.version = :version"),
    @NamedQuery(name = "VersionEntity.findByMajorVersion", query = "SELECT v FROM VersionEntity v WHERE v.majorVersion = :majorVersion"),
    @NamedQuery(name = "VersionEntity.findByMinorVersion", query = "SELECT v FROM VersionEntity v WHERE v.minorVersion = :minorVersion")})
public class VersionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "version_id")
    private Integer versionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;
    @Basic(optional = false)
    @NotNull
    @Column(name = "major_version")
    private int majorVersion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "minor_version")
    private int minorVersion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "browserVersionId", fetch = FetchType.EAGER)
    private List<UserAgentEntity> userAgentEntityList;

    public VersionEntity() {
    }

    public VersionEntity(Integer versionId) {
        this.versionId = versionId;
    }

    public VersionEntity(Integer versionId, int version, int majorVersion, int minorVersion) {
        this.versionId = versionId;
        this.version = version;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    @XmlTransient
    public List<UserAgentEntity> getUserAgentEntityList() {
        return userAgentEntityList;
    }

    public void setUserAgentEntityList(List<UserAgentEntity> userAgentEntityList) {
        this.userAgentEntityList = userAgentEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (versionId != null ? versionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VersionEntity)) {
            return false;
        }
        VersionEntity other = (VersionEntity) object;
        if ((this.versionId == null && other.versionId != null) || (this.versionId != null && !this.versionId.equals(other.versionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.statistic.VersionEntity[ versionId=" + versionId + " ]";
    }
    
}

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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "user_agent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAgentEntity.findAll", query = "SELECT u FROM UserAgentEntity u"),
    @NamedQuery(name = "UserAgentEntity.findByUserAgentId", query = "SELECT u FROM UserAgentEntity u WHERE u.userAgentId = :userAgentId"),
    @NamedQuery(name = "UserAgentEntity.findByBrowser", query = "SELECT u FROM UserAgentEntity u WHERE u.browser = :browser"),
    @NamedQuery(name = "UserAgentEntity.findByOperatingSystem", query = "SELECT u FROM UserAgentEntity u WHERE u.operatingSystem = :operatingSystem")})
public class UserAgentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_agent_id")
    private Long userAgentId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "browser")
    @Enumerated(EnumType.ORDINAL)
    private Browser browser;

    @Basic(optional = false)
    @NotNull
    @Column(name = "operating_system")
    @Enumerated(EnumType.ORDINAL)
    private OperatingSystem operatingSystem;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAgent", fetch = FetchType.EAGER)
    private List<StatisticEntity> statisticEntityList;

    @JoinColumn(name = "browser_version_id", referencedColumnName = "version_id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private VersionEntity browserVersion;

    public UserAgentEntity() {
    }

    public UserAgentEntity(Long userAgentId) {
        this.userAgentId = userAgentId;
    }

    public UserAgentEntity(Browser browser, VersionEntity browserVersion, OperatingSystem operatingSystem) {
        this.browser = browser;
        this.browserVersion = browserVersion;
        this.operatingSystem = operatingSystem;
    }

    public Long getUserAgentId() {
        return userAgentId;
    }

    public void setUserAgentId(Long userAgentId) {
        this.userAgentId = userAgentId;
    }

    public Browser getBrowser() {
        return browser;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    @XmlTransient
    public List<StatisticEntity> getStatisticEntityList() {
        return statisticEntityList;
    }

    public void setStatisticEntityList(List<StatisticEntity> statisticEntityList) {
        this.statisticEntityList = statisticEntityList;
    }

    public VersionEntity getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(VersionEntity browserVersion) {
        this.browserVersion = browserVersion;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime + ((browser == null) ? 0 : browser.getId());
		result = prime * result + ((browserVersion == null) ? 0 : browserVersion.hashCode());
		result = prime * result + ((operatingSystem == null) ? 0 : operatingSystem.getId());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof UserAgentEntity ? obj.hashCode()==hashCode() : false;
	}

    @Override
    public String toString() {
        return "jk.web.entities.statistic.UserAgentEntity[ userAgentId=" + userAgentId + " ]";
    }
    
}

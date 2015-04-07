/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.statistic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "statistic")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatisticEntity.findAll", query = "SELECT s FROM StatisticEntity s"),
    @NamedQuery(name = "StatisticEntity.findByStatisticId", query = "SELECT s FROM StatisticEntity s WHERE s.statisticId = :statisticId"),
    @NamedQuery(name = "StatisticEntity.findByLoginId", query = "SELECT s FROM StatisticEntity s WHERE s.loginId = :loginId"),
    @NamedQuery(name = "StatisticEntity.findByDate", query = "SELECT s FROM StatisticEntity s WHERE s.date = :date")})
public class StatisticEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "statistic_id")
    private Long statisticId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "login_Id")
    private Long loginId;

    @Basic(optional = true)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @JoinColumn(name = "ip_address_id", referencedColumnName = "ip_addresses_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private IpAddressEntity ipAddress;

    @JoinColumn(name = "user_agent_id", referencedColumnName = "user_agent_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UserAgentEntity userAgent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statisticEntity", fetch = FetchType.EAGER)
    private List<StatisticRequestUrlEntity> statisticRequestUrlEntityList;

    public StatisticEntity() {
    }

    public StatisticEntity(Long statisticId) {
        this.statisticId = statisticId;
    }

    public StatisticEntity(Long statisticId, Long loginId, Date date) {
        this.statisticId = statisticId;
        this.loginId = loginId;
        this.date = date;
    }

    public Long getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(Long statisticId) {
        this.statisticId = statisticId;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public IpAddressEntity getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(IpAddressEntity ipAddress) {
        this.ipAddress = ipAddress;
    }

    public UserAgentEntity getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(UserAgentEntity userAgent) {
        this.userAgent = userAgent;
    }

    @XmlTransient
    public List<StatisticRequestUrlEntity> getStatisticRequestUrlEntityList() {
        return statisticRequestUrlEntityList;
    }

    public void setStatisticRequestUrlEntityList(List<StatisticRequestUrlEntity> statisticRequestUrlEntityList) {
        this.statisticRequestUrlEntityList = statisticRequestUrlEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statisticId != null ? statisticId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatisticEntity)) {
            return false;
        }
        StatisticEntity other = (StatisticEntity) object;
        if ((this.statisticId == null && other.statisticId != null) || (this.statisticId != null && !this.statisticId.equals(other.statisticId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.statistic.StatisticEntity[ statisticId=" + statisticId + " ]";
    }
    
}

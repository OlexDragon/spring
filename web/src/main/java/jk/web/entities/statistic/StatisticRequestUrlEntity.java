/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.statistic;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "statistic_request_urls")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatisticRequestUrlEntity.findAll", query = "SELECT s FROM StatisticRequestUrlEntity s"),
    @NamedQuery(name = "StatisticRequestUrlEntity.findByStatisticId", query = "SELECT s FROM StatisticRequestUrlEntity s WHERE s.statisticRequestUrlEntityPK.statisticId = :statisticId"),
    @NamedQuery(name = "StatisticRequestUrlEntity.findByRequestUrlId", query = "SELECT s FROM StatisticRequestUrlEntity s WHERE s.statisticRequestUrlEntityPK.requestUrlId = :requestUrlId"),
    @NamedQuery(name = "StatisticRequestUrlEntity.findByTimes", query = "SELECT s FROM StatisticRequestUrlEntity s WHERE s.times = :times")})
public class StatisticRequestUrlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected StatisticRequestUrlEntityPK statisticRequestUrlEntityPK;

    @Basic(optional = false)
    @NotNull
    @Column(name = "times")
    private Integer times = 1;

    @JoinColumn(name = "request_url_id", referencedColumnName = "request_url_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private RequestUrlEntity requestUrlEntity;

    @JoinColumn(name = "statistic_id", referencedColumnName = "statistic_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private StatisticEntity statisticEntity;

    private Timestamp accessTime;

    public StatisticRequestUrlEntity() {
    }

    public StatisticRequestUrlEntity(StatisticRequestUrlEntityPK statisticRequestUrlEntityPK) {
        this.statisticRequestUrlEntityPK = statisticRequestUrlEntityPK;
    }

    public StatisticRequestUrlEntity(StatisticRequestUrlEntityPK statisticRequestUrlEntityPK, int times) {
        this.statisticRequestUrlEntityPK = statisticRequestUrlEntityPK;
        this.times = times;
    }

    public StatisticRequestUrlEntity(Long statisticId, Long requestUrlId) {
        this.statisticRequestUrlEntityPK = new StatisticRequestUrlEntityPK(statisticId, requestUrlId);
    }

    public StatisticRequestUrlEntityPK getStatisticRequestUrlEntityPK() {
        return statisticRequestUrlEntityPK;
    }

    public void setStatisticRequestUrlEntityPK(StatisticRequestUrlEntityPK statisticRequestUrlEntityPK) {
        this.statisticRequestUrlEntityPK = statisticRequestUrlEntityPK;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public RequestUrlEntity getRequestUrlEntity() {
        return requestUrlEntity;
    }

    public void setRequestUrlEntity(RequestUrlEntity requestUrlEntity) {
        this.requestUrlEntity = requestUrlEntity;
    }

    public StatisticEntity getStatisticEntity() {
        return statisticEntity;
    }

    public void setStatisticEntity(StatisticEntity statisticEntity) {
        this.statisticEntity = statisticEntity;
    }

    public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Timestamp accessTime) {
		this.accessTime = accessTime;
	}

	@Override
    public int hashCode() {
        return 31 + (statisticRequestUrlEntityPK != null ? statisticRequestUrlEntityPK.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatisticRequestUrlEntity)) {
            return false;
        }
        StatisticRequestUrlEntity other = (StatisticRequestUrlEntity) object;
        if ((this.statisticRequestUrlEntityPK == null && other.statisticRequestUrlEntityPK != null) || (this.statisticRequestUrlEntityPK != null && !this.statisticRequestUrlEntityPK.equals(other.statisticRequestUrlEntityPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.statistic.StatisticRequestUrlEntity[ statisticRequestUrlEntityPK=" + statisticRequestUrlEntityPK + " ]";
    }
    
}

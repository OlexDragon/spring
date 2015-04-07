/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.statistic;

import java.io.Serializable;
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
@Table(name = "statistic_request_url")
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
    private int times;
    @JoinColumn(name = "request_url_id", referencedColumnName = "request_url_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private RequestUrlEntity requestUrlEntity;
    @JoinColumn(name = "statistic_id", referencedColumnName = "statistic_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private StatisticEntity statisticEntity;

    public StatisticRequestUrlEntity() {
    }

    public StatisticRequestUrlEntity(StatisticRequestUrlEntityPK statisticRequestUrlEntityPK) {
        this.statisticRequestUrlEntityPK = statisticRequestUrlEntityPK;
    }

    public StatisticRequestUrlEntity(StatisticRequestUrlEntityPK statisticRequestUrlEntityPK, int times) {
        this.statisticRequestUrlEntityPK = statisticRequestUrlEntityPK;
        this.times = times;
    }

    public StatisticRequestUrlEntity(int statisticId, int requestUrlId) {
        this.statisticRequestUrlEntityPK = new StatisticRequestUrlEntityPK(statisticId, requestUrlId);
    }

    public StatisticRequestUrlEntityPK getStatisticRequestUrlEntityPK() {
        return statisticRequestUrlEntityPK;
    }

    public void setStatisticRequestUrlEntityPK(StatisticRequestUrlEntityPK statisticRequestUrlEntityPK) {
        this.statisticRequestUrlEntityPK = statisticRequestUrlEntityPK;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statisticRequestUrlEntityPK != null ? statisticRequestUrlEntityPK.hashCode() : 0);
        return hash;
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

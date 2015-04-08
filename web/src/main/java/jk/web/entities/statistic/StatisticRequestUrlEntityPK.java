/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jk.web.entities.statistic;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alex
 */
@Embeddable
public class StatisticRequestUrlEntityPK implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @NotNull
    @Column(name = "statistic_id")
    private Long statisticId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "request_url_id")
    private Long requestUrlId;

    public StatisticRequestUrlEntityPK() {
    }

    public StatisticRequestUrlEntityPK(Long statisticId, Long requestUrlId) {
        this.statisticId = statisticId;
        this.requestUrlId = requestUrlId;
    }

    public Long getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(Long statisticId) {
        this.statisticId = statisticId;
    }

    public Long getRequestUrlId() {
        return requestUrlId;
    }

    public void setRequestUrlId(Long requestUrlId) {
        this.requestUrlId = requestUrlId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += statisticId!=null ? statisticId.hashCode() : 0;
        hash += requestUrlId!=null ? requestUrlId.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatisticRequestUrlEntityPK)) {
            return false;
        }
        StatisticRequestUrlEntityPK other = (StatisticRequestUrlEntityPK) object;
        if (this.statisticId != other.statisticId) {
            return false;
        }
        if (this.requestUrlId != other.requestUrlId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.statistic.StatisticRequestUrlEntityPK[ statisticId=" + statisticId + ", requestUrlId=" + requestUrlId + " ]";
    }
    
}

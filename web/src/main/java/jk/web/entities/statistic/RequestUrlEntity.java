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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "request_urls")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequestUrlEntity.findAll", query = "SELECT r FROM RequestUrlEntity r"),
    @NamedQuery(name = "RequestUrlEntity.findByRequestUrlId", query = "SELECT r FROM RequestUrlEntity r WHERE r.requestUrlId = :requestUrlId"),
    @NamedQuery(name = "RequestUrlEntity.findByRequestUrl", query = "SELECT r FROM RequestUrlEntity r WHERE r.requestUrl = :requestUrl")})
public class RequestUrlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "request_url_id")
    private Integer requestUrlId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "request_url")
    private String requestUrl;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestUrlEntity", fetch = FetchType.EAGER)
    private List<StatisticRequestUrlEntity> statisticRequestUrlEntityList;

    public RequestUrlEntity() {
    }

    public RequestUrlEntity(Integer requestUrlId) {
        this.requestUrlId = requestUrlId;
    }

    public RequestUrlEntity(Integer requestUrlId, String requestUrl) {
        this.requestUrlId = requestUrlId;
        this.requestUrl = requestUrl;
    }

    public Integer getRequestUrlId() {
        return requestUrlId;
    }

    public void setRequestUrlId(Integer requestUrlId) {
        this.requestUrlId = requestUrlId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
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
        hash += (requestUrlId != null ? requestUrlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestUrlEntity)) {
            return false;
        }
        RequestUrlEntity other = (RequestUrlEntity) object;
        if ((this.requestUrlId == null && other.requestUrlId != null) || (this.requestUrlId != null && !this.requestUrlId.equals(other.requestUrlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.statistic.RequestUrlEntity[ requestUrlId=" + requestUrlId + " ]";
    }
    
}

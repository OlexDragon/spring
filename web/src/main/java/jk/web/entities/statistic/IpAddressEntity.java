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
@Table(name = "ip_addresses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IpAddressEntity.findAll", query = "SELECT i FROM IpAddressEntity i"),
    @NamedQuery(name = "IpAddressEntity.findByIpAddressesId", query = "SELECT i FROM IpAddressEntity i WHERE i.ipAddressesId = :ipAddressesId"),
    @NamedQuery(name = "IpAddressEntity.findByIpAddress", query = "SELECT i FROM IpAddressEntity i WHERE i.ipAddress = :ipAddress")})
public class IpAddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ip_addresses_id")
    private Long ipAddressesId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ip_address")
    private String ipAddress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ipAddress", fetch = FetchType.EAGER)
    private List<StatisticEntity> statisticEntityList;

    public IpAddressEntity() {
    }

    public IpAddressEntity(Long ipAddressesId) {
        this.ipAddressesId = ipAddressesId;
    }

    public IpAddressEntity(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getIpAddressesId() {
        return ipAddressesId;
    }

    public void setIpAddressesId(Long ipAddressesId) {
        this.ipAddressesId = ipAddressesId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @XmlTransient
    public List<StatisticEntity> getStatisticEntityList() {
        return statisticEntityList;
    }

    public void setStatisticEntityList(List<StatisticEntity> statisticEntityList) {
        this.statisticEntityList = statisticEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipAddressesId != null ? ipAddressesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpAddressEntity)) {
            return false;
        }
        IpAddressEntity other = (IpAddressEntity) object;
        if ((this.ipAddressesId == null && other.ipAddressesId != null) || (this.ipAddressesId != null && !this.ipAddressesId.equals(other.ipAddressesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jk.web.entities.statistic.IpAddressEntity[ ipAddressesId=" + ipAddressesId + " ]";
    }
    
}

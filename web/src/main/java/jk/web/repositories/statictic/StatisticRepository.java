package jk.web.repositories.statictic;

import java.util.Date;
import java.util.List;

import jk.web.entities.statistic.IpAddressEntity;
import jk.web.entities.statistic.StatisticEntity;
import jk.web.entities.statistic.UserAgentEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<StatisticEntity, Long> {

	public StatisticEntity findOneByLoginIdAndIpAddressAndUserAgentAndStatisticDate(Long userId, IpAddressEntity ipAddressEntity, UserAgentEntity userAgent, Date date);

	public List<StatisticEntity> findByStatisticDate(Date date);
}

package jk.web.repositories.statictic;

import jk.web.entities.statistic.StatisticRequestUrlEntity;
import jk.web.entities.statistic.StatisticRequestUrlEntityPK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRequestUrlsRepository extends JpaRepository<StatisticRequestUrlEntity, StatisticRequestUrlEntityPK> {
}

package jk.web.repositories.statictic;

import jk.web.entities.statistic.StatisticEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<StatisticEntity, Long> {
}

package jk.web.repositories.statictic;

import jk.web.entities.statistic.RequestUrlEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestUrlRepository extends JpaRepository<RequestUrlEntity, Long> {

	RequestUrlEntity getOneByRequestUrl(String requestUrl);

}

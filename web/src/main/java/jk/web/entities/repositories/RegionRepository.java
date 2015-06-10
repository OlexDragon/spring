package jk.web.entities.repositories;

import java.util.List;

import jk.web.entities.RegionEntity;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegionRepository extends JpaRepository<RegionEntity, Long> {

	@Query("SELECT re FROM RegionEntity re WHERE re.countryEntity.countryCode = :countryCode")
	List<RegionEntity> findByCountryCode(@Param("countryCode") String countryCode, Sort sort);

	@Query("SELECT re FROM RegionEntity re WHERE re.countryEntity.countryCode = :geonameId")
	List<RegionEntity> findBbyCountryGeonaeId(@Param("geonameId") Long countryGeonameId, Sort sort);
}

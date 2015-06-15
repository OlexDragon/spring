package jk.web.entities.repositories;

import java.util.List;

import jk.web.entities.RegionEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<RegionEntity, Long> {

	List<RegionEntity> findByCountryEntityCountryCodeOrderByRegionNameAsc(String countryCode);

	List<RegionEntity> findByCountryEntityGeonamesIdOrderByRegionNameAsc(Long geonameId);
}

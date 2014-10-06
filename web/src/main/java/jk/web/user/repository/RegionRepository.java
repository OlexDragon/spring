package jk.web.user.repository;

import java.util.List;

import jk.web.user.entities.RegionEntity;
import jk.web.user.entities.RegionEntityPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegionRepository extends JpaRepository<RegionEntity, RegionEntityPK> {

	@Query("select r from region r where r.regionEntityPK.countryCode = ?1")
	public List<RegionEntity> findByCountryCode(String countryCode);
}

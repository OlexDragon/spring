package jk.web.repositories.user;

import java.util.List;

import jk.web.entities.user.RegionEntity;
import jk.web.entities.user.RegionEntityPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegionRepository extends JpaRepository<RegionEntity, RegionEntityPK> {

	@Query("select r from region r where r.regionEntityPK.countryCode = :countryCode")
	public List<RegionEntity> findByCountryCode(@Param("countryCode") String countryCode);
}

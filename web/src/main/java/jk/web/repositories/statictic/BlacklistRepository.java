package jk.web.repositories.statictic;

import java.util.List;

import jk.web.entities.BlacklistEntity;
import jk.web.entities.BlacklistEntity.BlackListType;
import jk.web.entities.BlacklistEntityPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlacklistRepository extends JpaRepository<BlacklistEntity, BlacklistEntityPK> {

	@Query("SELECT bl FROM BlacklistEntity bl WHERE bl.blacklistEntityPK.blacklistType=:blackListType")
	List<BlacklistEntity> findByBlacklistType(@Param(value = "blackListType") BlackListType blackListType);
}

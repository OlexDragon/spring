package jk.web.repositories.statictic;

import java.util.List;

import jk.web.entities.BlacklistEntity;
import jk.web.entities.BlacklistEntity.BlackListType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<BlacklistEntity, Long> {

	List<BlacklistEntity> findByBlacklistType(BlackListType blackListType);
}

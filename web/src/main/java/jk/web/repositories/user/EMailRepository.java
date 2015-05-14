package jk.web.repositories.user;

import jk.web.entities.user.EMailEntity;
import jk.web.entities.user.EMailEntity.EMailStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EMailRepository extends JpaRepository<EMailEntity, Long> {

	EMailEntity findByEMail(String eMail);


	@Query("SELECT count(e.id)>0 FROM email e WHERE e.eMail = :eMail")
	boolean exists(@Param("eMail") String eMail);

	EMailEntity findOneByUserIdAndStatus(Long loginId, EMailStatus eMailStatus);
}

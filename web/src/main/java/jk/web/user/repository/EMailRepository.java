package jk.web.user.repository;

import jk.web.user.entities.EMailEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EMailRepository extends JpaRepository<EMailEntity, Long> {

	EMailEntity findByEMail(String eMail);


	@Query("SELECT count(e.id)>0 FROM email e WHERE e.eMail = ?1")
	boolean exists(String eMail);
}

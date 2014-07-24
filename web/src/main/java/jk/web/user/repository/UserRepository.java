package jk.web.user.repository;

import jk.web.user.entities.UserEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long>  {

	@Query("SELECT count(l.id)>0 FROM login l WHERE l.username = ?1")
	boolean exists(String username);

	@Query("SELECT count(e.id)>0 FROM emails e WHERE e.eMail = ?1")
	boolean existsEMail(String eMail);

	@Query("SELECT u FROM user u JOIN u.loginEntity l WITH l.username = ?1")
	UserEntity findByUsername(String username);
}

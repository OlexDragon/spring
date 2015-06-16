package jk.web.repositories.user;

import jk.web.entities.user.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query("SELECT count(l.id)>0 FROM login l WHERE l.username = :username")
	public boolean exists(@Param("username") String username);

	@Query("SELECT u FROM user u JOIN u.loginEntity l LEFT JOIN l.emails e WHERE l.username = :username")
	public UserEntity findByUsername(@Param("username") String username);

	@Query("SELECT u FROM user u JOIN u.loginEntity l LEFT JOIN l.emails e WITH e.email = :eMail")
	public UserEntity findByEMail(@Param("eMail") String eMail);
}

package jk.web.user.repository;

import jk.web.user.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query("SELECT count(l.id)>0 FROM login l WHERE l.username = ?")
	public boolean exists(String username);

	@Query("SELECT u FROM user u JOIN u.loginEntity l LEFT JOIN l.emails e WHERE l.username = ?")
	public UserEntity findByUsername(String username);

	@Query("SELECT u FROM user u JOIN u.loginEntity l LEFT JOIN l.emails e WITH e.eMail = ?")
	public UserEntity findByEMail(String eMail);
}

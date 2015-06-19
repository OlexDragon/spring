package jk.web.entities.user.repositories;

import jk.web.entities.user.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query("SELECT count(l.id)>0 FROM login l WHERE l.username = :username")
	public boolean exists(@Param("username") String username);

	public UserEntity findOneByLoginEntityUsername(String username);
}

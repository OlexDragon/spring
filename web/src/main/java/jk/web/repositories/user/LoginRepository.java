package jk.web.repositories.user;

import jk.web.entities.user.LoginEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoginRepository  extends JpaRepository<LoginEntity, Long>  {

	public LoginEntity findByUsername(String username);

	@Query("SELECT count(l.id)>0 FROM login l WHERE l.username = :username")
	public boolean exists(@Param("username") String username);

	@Query("UPDATE login l SET l.username=:username WHERE l.id=:id")
	public int saveUsernae(@Param("id") Long id, @Param("username") String username);

	@Query(value="SELECT l FROM login l LEFT JOIN l.emails e WHERE l.username=:usernameOrEMail or e.eMail=:usernameOrEMail")
	public LoginEntity findByUsernameOrEMail(@Param("usernameOrEMail") String usernameOrEMail);

	public LoginEntity findById(Long userId);
}

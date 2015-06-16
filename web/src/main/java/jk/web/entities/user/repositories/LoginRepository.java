package jk.web.entities.user.repositories;

import jk.web.entities.user.LoginEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository  extends JpaRepository<LoginEntity, Long>  {

	public LoginEntity findOneByUsername(String username);
	public LoginEntity findOneByUsernameOrEmailsEmail(String username, String email);
	public LoginEntity findOneByEmailsEmail(String email);

//	@Query("SELECT count(l.id)>0 FROM login l WHERE l.username = :username")
//	public boolean exists(@Param("username") String username);
//
//	@Query("UPDATE login l SET l.username=:username WHERE l.id=:id")
//	public int saveUsernae(@Param("id") Long id, @Param("username") String username);
//
//	public LoginEntity findById(Long userId);
//
//	@Query(value="SELECT l.id FROM login l WHERE l.username=:username")
//	public Long getIdByUsername(@Param("username") String username);
}

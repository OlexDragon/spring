package jk.web.entities.user.repositories;

import jk.web.entities.user.LoginEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository  extends JpaRepository<LoginEntity, Long>  {

	public LoginEntity findOneByUsername(String username);
	public LoginEntity findOneByHasEmailsEmailEntityEmail(String email);
}

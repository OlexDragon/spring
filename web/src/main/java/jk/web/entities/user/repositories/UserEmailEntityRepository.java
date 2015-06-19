package jk.web.entities.user.repositories;

import jk.web.entities.user.UserEmailEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEmailEntityRepository  extends JpaRepository<UserEmailEntity, Long>  {

	UserEmailEntity findOneByEmail(String email);
}

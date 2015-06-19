package jk.web.entities.repositories;

import jk.web.entities.user.UserEmailEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContactEmailRepository extends JpaRepository<UserEmailEntity, Long> {

	public UserEmailEntity findOneByEmail(String email);
}

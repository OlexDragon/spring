package jk.web.entities.repositories;

import jk.web.entities.user.UserContactEmailEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContactEmailRepository extends JpaRepository<UserContactEmailEntity, Long> {

	public UserContactEmailEntity findOneByEmail(String email);
}

package jk.web.entities.repositories;

import jk.web.entities.ContactEmailEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactEmailRepository extends JpaRepository<ContactEmailEntity, Long> {

	public ContactEmailEntity findOneByEmail(String email);
}

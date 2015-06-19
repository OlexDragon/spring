package jk.web.entities.repositories;

import java.util.List;

import jk.web.entities.ContactUsEntity;
import jk.web.entities.ContactUsEntity.ContactUsStatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactUsRepository extends JpaRepository<ContactUsEntity, Long> {

	public List<ContactUsEntity> findByContactUsStatus(ContactUsStatus contactUsStatus);
}

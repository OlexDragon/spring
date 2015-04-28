package jk.web.entities.repositories;

import jk.web.entities.ReferenceNumberEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferenceNumberRepository extends JpaRepository<ReferenceNumberEntity, Long> {

	public ReferenceNumberEntity findOneByReferenceNumber(String referenceNumber);
}

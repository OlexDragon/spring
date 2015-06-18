package jk.web.entities.repositories;

import jk.web.entities.business.BusinessEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<BusinessEntity, Long> {

	BusinessEntity findByVatNumberAndCompanyName(String vatNumber, String companyName);
			

}

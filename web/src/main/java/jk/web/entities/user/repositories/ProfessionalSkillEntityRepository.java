package jk.web.entities.user.repositories;

import java.util.List;

import jk.web.entities.user.ProfessionalSkillEntity;
import jk.web.entities.user.ProfessionalSkillsPK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalSkillEntityRepository  extends JpaRepository<ProfessionalSkillEntity, ProfessionalSkillsPK>  {

	List<ProfessionalSkillEntity> findByUserEntityLoginEntityUsername(String username);
	List<ProfessionalSkillEntity> findByKeyLoginID(Long id);
}

package jk.web.entities.user.repositories;

import jk.web.entities.user.ProfessionalSkillEntity;
import jk.web.entities.user.ProfessionalSkillsPK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalSkillEntityRepository  extends JpaRepository<ProfessionalSkillEntity, ProfessionalSkillsPK>  {
}

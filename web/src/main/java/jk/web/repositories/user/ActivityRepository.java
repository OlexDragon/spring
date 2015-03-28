package jk.web.repositories.user;

import jk.web.entities.user.ActivityEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Long>{

}

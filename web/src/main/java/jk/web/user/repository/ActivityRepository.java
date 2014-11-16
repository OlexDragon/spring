package jk.web.user.repository;

import jk.web.user.entities.ActivityEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Long>{

}

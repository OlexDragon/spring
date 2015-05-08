package jk.web.entities.repositories;

import java.util.List;

import jk.web.entities.BackgroundImgEntity;
import jk.web.entities.BackgroundImgEntity.BackgroundImgStatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BackgroundImgRepository extends JpaRepository<BackgroundImgEntity, Long> {

	public List<BackgroundImgEntity> findByStatus(BackgroundImgStatus status);
}

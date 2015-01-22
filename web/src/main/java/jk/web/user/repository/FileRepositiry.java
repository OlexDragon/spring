package jk.web.user.repository;

import java.util.List;

import jk.web.user.entities.FileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepositiry extends JpaRepository<FileEntity, Long>{

	@Query("SELECT f.fileID FROM FileEntity f WHERE f.userID = ?1 and f.contentType like 'image%'")
	List<Long> findImagesIdsByUserId(Long userID);

	List<FileEntity> findByUserID(Long userID);

	List<FileEntity> findByUserIDAndShowToAll(Long userID, boolean b);
}

package jk.web.repositories.user;

import java.util.List;

import jk.web.entities.user.FileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileRepositiry extends JpaRepository<FileEntity, Long>{

	@Query("SELECT f.fileID FROM FileEntity f WHERE f.userID = :userID and f.contentType like 'image%'")
	List<Long> findImagesIdsByUserId(@Param("userID") Long userID);

	List<FileEntity> findByUserID(Long userID);

	List<FileEntity> findByUserIDAndShowToAll(Long userID, boolean b);
}

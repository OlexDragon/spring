package jk.web.user.repository;

import java.util.List;

import jk.web.user.entities.FileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepositiry extends JpaRepository<FileEntity, Long>{

	@Query("SELECT CONCAT('/res/images/', f.userID, '/small/', f.fileID,'.png') FROM FileEntity f WHERE f.userID = ?1")
	List<String> findFilesPathesByUserId(Long userID);
}

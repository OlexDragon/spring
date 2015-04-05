package jk.web.repositories.workers.search;

import java.util.List;

import jk.web.entities.workers.search.SearchCatgoryEntity;
import jk.web.entities.workers.search.SearchCatgoryEntity.CategoryStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SearchCatgoriesRepository extends JpaRepository<SearchCatgoryEntity, Long>{

	@Query("SELECT sc FROM SearchCatgoryEntity sc WHERE (sc.categoryName LIKE CONCAT('\"',:startWith,'%') OR sc.categoryName LIKE CONCAT(:startWith,'%')) AND sc.status = :categoryStatus")
	public List<SearchCatgoryEntity> findByCategoryNameStartingWithAndStatus(@Param("startWith") String startWith, @Param("categoryStatus") CategoryStatus categoryStatus);

	public List<SearchCatgoryEntity> findByStatus(CategoryStatus categoryStatus);

	@Query(value="SELECT DISTINCT UCASE(IF(SUBSTRING(`category_name`, 1, 1) = '\"', SUBSTRING(`category_name`, 2, 1),  SUBSTRING(`category_name`, 1, 1))) s FROM `jk`.`search_categories` WHERE `category_status`=1", nativeQuery = true)
	public List<String> findAvailableFirstLeters();

	@Query(value="SELECT DISTINCT SUBSTRING(sce.categoryName, 1, 1) FROM SearchCatgoryEntity sce")
	public List<String> findAvailableFirstCharacters();

	public SearchCatgoryEntity findOneByCategoryName(String name);

	public List<SearchCatgoryEntity> findByCategoryNameStartingWith(String startWith);
	public Page<SearchCatgoryEntity> findFirst50ByCategoryNameStartingWith(String startWith, Pageable pageable);

	public Page<SearchCatgoryEntity> findFirst10ByCategoryNameContaining( String searchFor, Pageable pageable);

	public Page<SearchCatgoryEntity> findFirst50ByCategoryNameContaining(String searchFor, Pageable pageable);
}

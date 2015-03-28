package jk.web.repositories.workers.search;

import java.util.List;

import jk.web.entities.workers.search.SearchCatgoriesEntity;
import jk.web.entities.workers.search.SearchCatgoriesEntity.CategoryStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SearchCatgoriesRepository extends JpaRepository<SearchCatgoriesEntity, Long>{

	@Query("SELECT sc FROM SearchCatgoriesEntity sc WHERE (sc.categoryName LIKE CONCAT('\"',:startWith,'%') OR sc.categoryName LIKE CONCAT(:startWith,'%')) AND sc.status = :categoryStatus")
	public List<SearchCatgoriesEntity> findByCategoryNameStartingWithAndStatus(@Param("startWith") String startWith, @Param("categoryStatus") CategoryStatus categoryStatus);

	public List<SearchCatgoriesEntity> findByStatus(CategoryStatus categoryStatus);

	@Query(value="SELECT DISTINCT UCASE(IF(SUBSTRING(`category_name`, 1, 1) = '\"', SUBSTRING(`category_name`, 2, 1),  SUBSTRING(`category_name`, 1, 1))) s FROM `jk`.`search_catgories` WHERE `category_status`=0", nativeQuery = true)
	public List<String> findAvailableFirstLeters();
}

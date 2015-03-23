package jk.web.workers.search.entities.repositories;

import java.util.List;

import jk.web.workers.search.entities.SearchCatgoriesEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SearchCatgoriesRepository extends JpaRepository<SearchCatgoriesEntity, Long>{

	public List<SearchCatgoriesEntity> findByCategoryNameStartingWith(String startWith);

	@Query(value="SELECT DISTINCT UCASE(IF(SUBSTRING(`category_name`, 1, 1) = '\"', SUBSTRING(`category_name`, 2, 1),  SUBSTRING(`category_name`, 1, 1))) s FROM `jk`.`search_catgories`", nativeQuery = true)
	public List<String> findAvailableFirstLeters();
}

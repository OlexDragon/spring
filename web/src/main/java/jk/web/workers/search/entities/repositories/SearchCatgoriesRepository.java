package jk.web.workers.search.entities.repositories;

import java.util.List;

import jk.web.workers.search.entities.SearchCatgoriesEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SearchCatgoriesRepository extends JpaRepository<SearchCatgoriesEntity, Long>{

	public List<SearchCatgoriesEntity> findByCategoryNameStartingWith(String startWith);

	@Query("SELECT DISTINCT SUBSTRING(sc.categoryName, 1, 1) FROM SearchCatgoriesEntity sc")
	public List<String> findAvailableFirstLeters();
}

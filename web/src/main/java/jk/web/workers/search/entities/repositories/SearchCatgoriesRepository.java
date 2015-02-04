package jk.web.workers.search.entities.repositories;

import java.util.List;

import jk.web.workers.search.entities.SearchCatgoriesEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchCatgoriesRepository extends JpaRepository<SearchCatgoriesEntity, Long>{

	public List<SearchCatgoriesEntity> findByCategoryNameStartingWith(String startWith);

}

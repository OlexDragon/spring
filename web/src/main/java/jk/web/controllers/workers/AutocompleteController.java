package jk.web.controllers.workers;

import java.util.List;

import jk.web.entities.workers.search.SearchCatgoryEntity;
import jk.web.repositories.workers.search.SearchCatgoriesRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autocomplete")
public class AutocompleteController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SearchCatgoriesRepository searchCatgoriesRepository;

	@RequestMapping(value="search/categories", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String[]> searchCategories(@RequestParam(value="term", required=false) String category){
		logger.entry(category);

		String[] categories;
		if(category!=null && !(category=category.trim()).isEmpty()){
			Page<SearchCatgoryEntity> page = searchCatgoriesRepository.findFirst10ByCategoryNameContaining(category, new PageRequest(0, 10, new Sort(Direction.ASC, "categoryName")));
			List<SearchCatgoryEntity> ces = page.getContent();
			categories = new String[ces.size()];
			for(int i=0; i<categories.length; i++)
				categories[i]= ces.get(i).getCategoryName();
		}else
			categories = null;

		return logger.exit(new ResponseEntity<String[]>(categories, HttpStatus.CREATED));
	}
}

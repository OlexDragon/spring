package jk.web.workers.search.controllers;

import java.util.List;

import jk.web.html.select.ContentDiv;
import jk.web.workers.SearchClass;
import jk.web.workers.SearchClass.SearchDetails;
import jk.web.workers.search.entities.SearchCatgoriesEntity;
import jk.web.workers.search.entities.repositories.SearchCatgoriesRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/search")
public class SearchController {

//	private static final long HTML_UL_LI_TAG = -100L;
//	private static final long HTML_H1_TAG = -1L;
//	private static final long HTML_P_TAG = 0L;

	private final Logger logger = LogManager.getLogger();

//	@Autowired
//	private UserWorker userWorker;
//	@Autowired
//	protected ApplicationContext applicationContext;
	@Autowired
	private SearchCatgoriesRepository searchCatgoriesRepository;

	@RequestMapping
	public String serch(@Param(value = "searchFor") String searchFor, @Param(value="searchDetails") SearchDetails searchDetails, Model model){
		logger.entry(searchFor, searchDetails);

		SearchClass searchClass = new SearchClass();
		searchClass.setSearchFor(searchFor);
		searchClass.setSearchDetails(searchDetails);
		ContentDiv contentDivs = searchClass.getContentDivs();
		model.addAttribute("title", "Search result");
		model.addAttribute("contents", contentDivs!=null ? contentDivs.toString() : "");
		model.addAttribute("message", "empty");
		return "message";
	}

	@RequestMapping(value="categories/{startWith}", method = RequestMethod.POST)
	public ResponseEntity<List<SearchCatgoriesEntity>> search(	@PathVariable String startWith){
		logger.entry(startWith);

		List<SearchCatgoriesEntity> categories;
		try{
			if(startWith.equalsIgnoreCase("all"))
				categories = searchCatgoriesRepository.findAll();
			else
				categories = searchCatgoriesRepository.findByCategoryNameStartingWith(startWith);
		}catch(Exception ex){
			logger.catching(ex);
			categories = null;
		}
		logger.trace("\n\t{}", categories);

//		Locale locale = userWorker.getLocale();
		HttpStatus status;
		if(categories==null || categories.size()==0){
//			categories = new ArrayList<>();
//			categories.add(new SearchCatgoriesEntity(HTML_P_TAG, applicationContext.getMessage( "SearchController.find.X_result_s", new String[]{"0"}, "Find 0 result" , locale)));
//			categories.add(new SearchCatgoriesEntity(HTML_H1_TAG, applicationContext.getMessage( "SearchController.find.no_results_for_X", new String[]{startWith}, "Sorry, but we didn't find any results" , locale)));
//			categories.add(new SearchCatgoriesEntity(HTML_UL_LI_TAG, applicationContext.getMessage( "SearchController.find.make_sure.spelled_correctly", null, "Make sure all words are spelled correctly." , locale)));
//			categories.add(new SearchCatgoriesEntity(HTML_UL_LI_TAG, applicationContext.getMessage( "SearchController.find.try.keywords.different", null, "Try different keywords." , locale)));
//			categories.add(new SearchCatgoriesEntity(HTML_UL_LI_TAG, applicationContext.getMessage( "SearchController.find.try.keywords.general", null, "Try more general keywords." , locale)));
			status = HttpStatus.NOT_FOUND;
		}else{
			categories.add(0, new SearchCatgoriesEntity(0L, startWith));
			status = HttpStatus.OK;
		}

		return logger.exit(new ResponseEntity<>(categories,  status));
	}
}

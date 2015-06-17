package jk.web.controllers.workers.search;

import java.util.List;

import jk.web.entities.workers.search.SearchCatgoryEntity;
import jk.web.entities.workers.search.SearchCatgoryEntity.CategoryStatus;
import jk.web.html.select.ContentDiv;
import jk.web.repositories.workers.search.SearchCatgoriesRepository;
import jk.web.workers.SearchClass;
import jk.web.workers.SearchClass.SearchDetails;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("search")
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

	private List<String> letters;

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

	@RequestMapping(value="categories/{startWith}")
	public String searchCategories(	@PathVariable String startWith, Model model){
		logger.entry(startWith);

		List<SearchCatgoryEntity> categories;
		try{
			if(startWith.equalsIgnoreCase("all"))
				categories = searchCatgoriesRepository.findByStatus(CategoryStatus.SHOW);
			else
				categories = searchCatgoriesRepository.findByCategoryNameStartingWithAndStatus(startWith, CategoryStatus.SHOW);
		}catch(Exception ex){
			logger.catching(ex);
			categories = null;
		}
		logger.trace("\n\t{}", categories);
		model.addAttribute("categories", categories);

		return "search :: categories";
	}

	@RequestMapping("available-letters")
	public String getAvailableLetters(Model model) {
		logger.entry();

		model.addAttribute("availableLetters", getAvailableLetters());

		return "search :: available-letters";
	}

	public List<String> getAvailableLetters() {
		if(letters==null)
			letters = searchCatgoriesRepository.findAvailableFirstLeters();

		return letters;
	}
}

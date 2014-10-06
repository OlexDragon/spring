package jk.web.workers.controllers;

import jk.web.html.select.ContentDiv;
import jk.web.workers.SearchClass;
import jk.web.workers.SearchClass.SearchDetails;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {

	private final Logger logger = LogManager.getLogger();

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
}

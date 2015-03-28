package jk.web.controllers.management;

import jk.web.repositories.workers.search.SearchCatgoriesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management")
public class Management {

	@Autowired
	private SearchCatgoriesRepository searchCatgoriesRepository;

	@PreAuthorize("hasAuthority('MANAGER')")
	@RequestMapping("search/categories")
	public String serchCategories(Model model){

		model.addAttribute("letters", searchCatgoriesRepository.findAvailableFirstLeters());

		return "management/categories";
	}
}

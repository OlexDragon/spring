package jk.web;

import java.util.List;

import jk.web.repositories.workers.search.SearchCatgoriesRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
@ComponentScan
public class HomeController {

	private final static Logger logger = LogManager.getLogger();

	@Autowired
	private SearchCatgoriesRepository searchCatgoriesRepository;

	private List<String> letters;

	@RequestMapping({ "/", "/home", "/index" })
	public String home(Model model, @CookieValue(value="location", required=false) String locationCooky, @ModelAttribute("clientIpAddress") String clientIpAddress) {
		logger.entry( locationCooky, clientIpAddress);

		if(locationCooky!=null && !locationCooky.isEmpty())
			model.addAttribute("location", locationCooky);

		model.addAttribute("letters", getAvailableLetters());

		return "search";
	}

	public List<String> getAvailableLetters() {
		if(letters==null)
			letters = searchCatgoriesRepository.findAvailableFirstLeters();
		return letters;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HomeController.class, args);
	}
}

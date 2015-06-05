package jk.web;

import jk.web.repositories.workers.search.SearchCatgoriesRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class HomeController {

	private final static Logger logger = LogManager.getLogger();

	@Autowired
	private SearchCatgoriesRepository searchCatgoriesRepository;

	@RequestMapping({ "/", "/home", "/index" })
	public String home(Model model, @CookieValue(value="location", required=false) String locationCooky, @ModelAttribute("clientIpAddress") String clientIpAddress) {
		logger.entry( locationCooky, clientIpAddress);

		if(locationCooky!=null && !locationCooky.isEmpty())
			model.addAttribute("location", locationCooky);

		return "search";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HomeController.class, args);
	}
}

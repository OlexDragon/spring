package jk.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

	private static final List<String> CHARACTERS = new ArrayList<String>();
	static{
		for(char ch='A'; ch<='Z'; ch++)
			CHARACTERS.add(Character.toString(ch));
	}

	private final Logger logger = LogManager.getLogger();
	@Autowired
	private SearchCatgoriesRepository searchCatgoriesRepository;

	@ModelAttribute("clientIpAddress")
	public String populateClientIpAddress( HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null)
			   ipAddress = request.getRemoteAddr();  
		   return ipAddress;
	}

	@RequestMapping({ "/", "/home", "/index" })
	public String home(Model model, @CookieValue(value="location", required=false) String locationCooky, @ModelAttribute("clientIpAddress") String clientIpAddress) {
		logger.entry( locationCooky, clientIpAddress);

		if(locationCooky!=null && !locationCooky.isEmpty())
			model.addAttribute("location", locationCooky);

		List<String> letters = searchCatgoriesRepository.findAvailableFirstLeters();
		logger.trace("\n\tLetters: {}", letters);
		model.addAttribute("letters", letters);
		model.addAttribute("CHARACTERS", CHARACTERS);
		model.addAttribute("bgImage", getBgImagePath());

		return "search";
	}

	private String getBgImagePath() {
		Calendar calendar = Calendar.getInstance();
		String path;
		int thehour = calendar.get(Calendar.HOUR_OF_DAY);

		if (thehour >= 18) {
			// evening
			path = "/images/background/20.jpg";
		} else if (thehour >= 15) {
			// afternoon
			path = "/images/background/19.jpg";
		} else if (thehour >= 12) {
			// noon
			path = "/images/background/18.jpg";
		} else if (thehour >= 7) {
			// morning
			path = "/images/background/21.jpg";
		} else {
			// night
			path = "/images/background/20.jpg";
		}
		return path;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HomeController.class, args);
	}
}

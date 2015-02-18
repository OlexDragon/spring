package jk.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
@ComponentScan
public class HomeController {

	private final Logger logger = LogManager.getLogger();

	@RequestMapping({ "/", "/home", "/index" })
	public String home(Model model, @CookieValue(value="location", required=false) String locationCooky) {
		logger.entry( locationCooky);

		model.addAttribute("location", locationCooky);
		return "search";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HomeController.class, args);
	}
}

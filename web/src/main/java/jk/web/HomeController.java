package jk.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private final Logger logger = LogManager.getLogger();

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

		return "search";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HomeController.class, args);
	}
}

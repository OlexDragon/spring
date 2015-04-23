package jk.web;

import java.util.Calendar;
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

	public static String getBgImagePath() {
		Calendar calendar = Calendar.getInstance();
		String path;
		int thehour = calendar.get(Calendar.HOUR_OF_DAY);

		if (thehour >= 18) {
			// evening
			path = "https://scontent-lga.xx.fbcdn.net/hphotos-xpa1/v/t1.0-9/s720x720/18293_514588762015384_7037405496006114391_n.jpg?oh=0f86b62797ab3a957145e6ddcda2f8b3&oe=55A1676C";///images/background/20.jpg";
		} else if (thehour >= 15) {
			// afternoon
			path = "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/s720x720/11072904_514588905348703_27063662908001677_n.jpg?oh=f82ceef4786585b3b5044883480b7314&oe=55A1C1EE&__gda__=1437653371_0175e9fee14510c4783194415db9e6d1";///images/background/19.jpg";
		} else if (thehour >= 12) {
			// noon
			path = "https://fbcdn-sphotos-f-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/q81/s720x720/10422257_514588908682036_3567941355937352193_n.jpg?oh=33a9fb8a97f8d95cb36bd91661605f9d&oe=55982745&__gda__=1436590828_08d611061239a3d8bc75fa347dbd9092";///images/background/18.jpg";
		} else if (thehour >= 7) {
			// morning
			path = "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xtp1/v/t1.0-9/s720x720/11110884_514588745348719_1960553605335542264_n.jpg?oh=f7a406a436b1fdcf015adf091d966c48&oe=55E33AF9&__gda__=1437460846_4efa191621d921e751a46f47f9f537be";///images/background/21.jpg";
		} else {
			// night
			path = "https://scontent-lga.xx.fbcdn.net/hphotos-xpa1/v/t1.0-9/s720x720/18293_514588762015384_7037405496006114391_n.jpg?oh=0f86b62797ab3a957145e6ddcda2f8b3&oe=55A1676C";///images/background/20.jpg";
		}
		return path;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HomeController.class, args);
	}
}

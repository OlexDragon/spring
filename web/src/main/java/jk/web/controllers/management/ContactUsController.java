package jk.web.controllers.management;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping("/management/contactUs")
public class ContactUsController {

	private final Logger logger = LogManager.getLogger();

	@RequestMapping
	public String getUnansweredMessages(){
		logger.entry();
		return "unanswered";
	}
}

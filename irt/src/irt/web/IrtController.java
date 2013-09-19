package irt.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IrtController {
	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping("/")
	public String welcome() {
		return "welcome";
	}

	@RequestMapping("/signup.htm")
	public String launchSignup() {
		return "signup";
	}
}

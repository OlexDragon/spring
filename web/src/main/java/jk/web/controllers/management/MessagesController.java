package jk.web.controllers.management;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping("/management/messages")
public class MessagesController {

	@RequestMapping
	public String messages(){
		return "management/messages";
	}
}

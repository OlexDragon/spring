package jk.web.controllers.management;

import jk.web.beans.view.management.SearchCategoryView;
import jk.web.view.components.LoginsView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping("/management/users")
public class ManagementUsersController {

	private final Logger logger = LogManager.getLogger();

	@Lazy
	@Autowired
	private LoginsView usersView;

	@ModelAttribute("usersView")
	public LoginsView  attrUsersView(){
		return usersView;
	}

	@RequestMapping(method=RequestMethod.GET)
	public String users(SearchCategoryView searchCategoryView, Model model){

		return "management/users";
	}
}

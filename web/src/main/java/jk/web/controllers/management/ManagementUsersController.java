package jk.web.controllers.management;

import java.util.Map;

import jk.web.beans.view.management.SearchCategoryView;
import jk.web.entities.user.LoginEntity;
import jk.web.entities.user.LoginEntity.Permission;
import jk.web.view.components.LoginsView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	@RequestMapping(value="**", method=RequestMethod.GET)
	public String users(SearchCategoryView searchCategoryView){

		return "management/users";
	}

	@RequestMapping(value="edit", method=RequestMethod.POST)
	public String editUsers(SearchCategoryView searchCategoryView, @RequestParam Map<String, String> allParams){
		logger.entry(allParams);
		String idStr = allParams.get("id");
		if (idStr != null) {
			long id = Long.parseLong(idStr);
			LoginEntity loginEntity = usersView.gerLogin(id);
			if (loginEntity != null) {
				long permissions = 0;
				for (Permission p : Permission.values()) {
					if (allParams.containsKey(p.name()))
						permissions = Permission.addPermission(permissions, p);
				}
				if(loginEntity.getPermissions()!=null ? !loginEntity.getPermissions().equals(permissions): permissions!=0){
					loginEntity.setPermissions(permissions);
					usersView.save(loginEntity);
				}
			}
		}

		return "management/users";
	}
}

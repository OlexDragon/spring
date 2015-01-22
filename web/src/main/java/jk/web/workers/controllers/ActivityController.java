package jk.web.workers.controllers;

import java.util.ArrayList;
import java.util.List;

import jk.web.data.beans.Activity;
import jk.web.user.entities.ActivityEntity;
import jk.web.user.entities.UserEntity;
import jk.web.user.repository.ActivityRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/activities")
public class ActivityController {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private ActivityRepository activityRepository;

	@RequestMapping
	public String news(Model model){

		List<ActivityEntity> activityEntities = activityRepository.findAll();

		List<Activity> activities = new ArrayList<>();
		model.addAttribute("activities", activities);

		if(activityEntities!=null){
			for(ActivityEntity ae:activityEntities)
				switch(ae.getType()){
				case NEW_USER:
					Activity activity = newUser(ae);
					if(activity!=null)
						activities.add(activity);
					break;
				default:
					break;
				}
		}
		return "activity";
	}

	private Activity newUser(ActivityEntity activityEntity) {
		logger.trace("\n\t{}\n\t{}", activityEntity);

		UserEntity userEntity = activityEntity.getUserEntity();
		Activity activity;
		if(userEntity!=null){
			activity = new Activity();
			activity.setTitle(userEntity.getFirstName()+" "+userEntity.getLastName());
			activity.setActivityType(activityEntity.getType().name());
//			List<String> paragraphs = new ArrayList<String>();
//			paragraphs.add("username - "+userEntity.getLoginEntity().getUsername());
//			List<ProfessionalSkillEntity> professionalSkills = userEntity.getProfessionalSkills();
//			if(professionalSkills!=null)
//				paragraphs.add("professionalSkills - "+professionalSkills);
//			activity.setParagraphs(paragraphs);
		}else
			activity = null;

		return activity;
	}
}

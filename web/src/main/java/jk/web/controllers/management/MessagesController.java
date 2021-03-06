package jk.web.controllers.management;

import java.security.Principal;
import java.util.List;

import jk.web.entities.ContactUsEntity;
import jk.web.entities.ContactUsEntity.ContactUsStatus;
import jk.web.entities.repositories.ContactUsRepository;
import jk.web.entities.user.LoginEntity;
import jk.web.entities.user.repositories.LoginRepository;
import jk.web.workers.email.ContactUsStatusUpdater;
import jk.web.workers.email.EMailWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping("/management/messages")
public class MessagesController {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private ContactUsRepository contactUsRepository;
	@Autowired
	private EMailWorker eMailWorker;

	private StringBuilder stringBuilder = new StringBuilder();
	private String lineSeparator = System.getProperty("line.separator");

	@ModelAttribute("tocontact")
	public List<ContactUsEntity>  attrUsersView(){
		return contactUsRepository.findByContactUsStatus(ContactUsStatus.TO_ANSWER);
	}

	@RequestMapping
	public String messages(){
		logger.entry();
		return "management/messages";
	}

	@RequestMapping(params="send")
	public String saveMessages(Principal principal, @RequestParam Long id, @RequestParam String message, Model model){
		logger.entry(id, message);
		String username = principal.getName();
		LoginEntity loginId = loginRepository.findOneByUsername(username);
		if(loginId==null){
			model.addAttribute("error", "Can not find the login information for username: "+username);
		}else{
			ContactUsEntity contactUsEntity = contactUsRepository.findOne(id);

			synchronized (stringBuilder) {
				
				stringBuilder.setLength(0);
				stringBuilder.append(message);
				stringBuilder.append(lineSeparator);
				stringBuilder.append(lineSeparator);
				stringBuilder.append("Original message:");
				stringBuilder.append(lineSeparator);
				stringBuilder.append("From: ");
				stringBuilder.append(contactUsEntity.getName());
				stringBuilder.append(lineSeparator);
				stringBuilder.append(contactUsEntity.getMessage());

				eMailWorker.sendEMail(
						contactUsEntity.getEmailEntity().getEmail(),
						contactUsEntity.getSubject(),
						stringBuilder.toString(),
						new ContactUsStatusUpdater(contactUsRepository, contactUsEntity, ContactUsStatus.IN_PROCESS, ContactUsStatus.ANSWERED));
			}
		}

		return "management/messages";
	}
}

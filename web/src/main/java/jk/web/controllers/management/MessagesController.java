package jk.web.controllers.management;

import java.security.Principal;
import java.util.List;

import jk.web.entities.ContactUsEntity;
import jk.web.entities.ContactUsEntity.ContactUsStatus;
import jk.web.repositories.user.LoginRepository;
import jk.web.workers.email.ContactUsStatusUpdater;

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
	private NewMessageInformer data = new NewMessageInformer();

	private StringBuilder stringBuilder = new StringBuilder();
	private String lineSeparator = System.getProperty("line.separator");

	public MessagesController(){
		
	}

	@ModelAttribute("tocontact")
	public List<ContactUsEntity>  attrUsersView(Model model){
		return data.contactUsRepository.findByContactStatus(ContactUsStatus.TO_CONTACT);
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
		Long loginId = loginRepository.getIdByUsername(username);
		if(loginId==null){
			model.addAttribute("error", "Can not find the login information for username: "+username);
		}else{
			ContactUsEntity contactUsEntity = data.contactUsRepository.findOne(id);

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

				data.eMailWorker.sendEMail(
						contactUsEntity.getContactEmailEntity().getEmail(),
						contactUsEntity.getSubject(),
						stringBuilder.toString(),
						new ContactUsStatusUpdater(data.contactUsRepository, contactUsEntity, ContactUsStatus.CONTACTED));
			}
		}

		return "management/messages";
	}
}

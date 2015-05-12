package jk.web.controllers.management;

import jk.web.entities.ContactUsEntity.ContactUsStatus;
import jk.web.entities.repositories.ContactUsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping("/management/messages")
public class MessagesController {

	@Autowired
	private ContactUsRepository contactUsRepository;

	@RequestMapping
	public String messages(Model model){

		model.addAttribute("tocontact", contactUsRepository.findByContactStatus(ContactUsStatus.TO_CONTACT));

		return "management/messages";
	}
}

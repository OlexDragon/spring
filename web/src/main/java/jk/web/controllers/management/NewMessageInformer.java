package jk.web.controllers.management;

import java.util.List;

import jk.web.entities.ContactUsEntity;
import jk.web.entities.ContactUsEntity.ContactUsStatus;
import jk.web.entities.repositories.ContactUsRepository;
import jk.web.workers.email.EMailWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NewMessageInformer {

	private final Logger logger = LogManager.getLogger();

	//Values from application.properties file
	@Value("${jk.email.from}")
	private String emaleFrom;

	@Autowired
	public ContactUsRepository contactUsRepository;
	@Autowired
	public EMailWorker eMailWorker;

	@Scheduled(fixedRate = 24*60*60*1000)
	public void checkFotNewMessages(){
		List<ContactUsEntity> contactUsEntities = contactUsRepository.findByContactUsStatus(ContactUsStatus.TO_ANSWER);
		logger.trace("\n\t{}", contactUsEntities);

		if(contactUsEntities!=null && contactUsEntities.size()>0){
			eMailWorker.sendEMail(emaleFrom, "new_messages", "We have new messagies to answer(http://www.fashionprofinder.com/management/messages)", null);
		}
	}
}
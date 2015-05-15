package jk.web.controllers.management;

import java.util.List;

import javax.annotation.PostConstruct;

import jk.web.entities.ContactUsEntity;
import jk.web.entities.ContactUsEntity.ContactUsStatus;
import jk.web.entities.repositories.ContactUsRepository;
import jk.web.workers.email.EMailWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NewMessageInformer {

	private final Logger logger = LogManager.getLogger();

	//Values from application.properties file
	@Value("${email.from}")
	private String emaleFrom;

	@Autowired
	public ContactUsRepository contactUsRepository;
	@Autowired
	public EMailWorker eMailWorker;

	@PostConstruct
	public void start() {
		logger.entry();
		Thread t = new Thread(target);
		int priority = t.getPriority();
		if(priority>Thread.MIN_PRIORITY)
			t.setPriority(priority-1);
		t.setDaemon(true);
		t.start();
	}

	private Runnable target = new Runnable() {
		
		@Override
		public void run() {
			logger.entry();
			while(true){
				List<ContactUsEntity> findByContactStatus = contactUsRepository.findByContactStatus(ContactUsStatus.TO_ANSWER);
				if(findByContactStatus!=null && findByContactStatus.size()>0){
					eMailWorker.sendEMail(emaleFrom, "new_messages", "We have new messagies to answer(http://www.fashionprofinder.com/management/messages)", null);
				}
				try {
					Thread.sleep(24*60*60*1000);
				} catch (InterruptedException e) {
					logger.catching(e);
				}
			}
		}
	};
}
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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NewMessageInformer {

	private final Logger logger = LogManager.getLogger();

	//Values from application.properties file
	@Value("${jk.email.from}")
	private String emaleFrom;
	@Value("${jk.messages.period}")
	private String period;

	@Autowired
	public ContactUsRepository contactUsRepository;
	@Autowired
	public EMailWorker eMailWorker;

	@PostConstruct
	public void start() {
		logger.entry();
		Thread thread = new Thread(target);
		int priority = thread.getPriority();
		if(priority>Thread.MIN_PRIORITY)
			thread.setPriority(priority-1);
		thread.setDaemon(true);
		thread.start();
	}

	private Runnable target = new Runnable() {
		
		@Override
		public void run() {
			logger.entry();

			int p = Integer.parseInt(period);

			while(true){
				try {
					Thread.sleep(p);
				} catch (InterruptedException e) {
					logger.catching(e);
				}
				List<ContactUsEntity> findByContactStatus = contactUsRepository.findByContactUsStatus(ContactUsStatus.TO_ANSWER);
				logger.trace("\n\t{}", findByContactStatus);

				if(findByContactStatus!=null && findByContactStatus.size()>0){
					eMailWorker.sendEMail(emaleFrom, "new_messages", "We have new messagies to answer(http://www.fashionprofinder.com/management/messages)", null);
				}
			}
		}
	};
}
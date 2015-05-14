package jk.web.workers.email;

import java.sql.Timestamp;
import java.util.Calendar;

import jk.web.entities.ContactUsEntity;
import jk.web.entities.ContactUsEntity.ContactUsStatus;
import jk.web.entities.repositories.ContactUsRepository;

public class ContactUsStatusUpdater implements StatusUpdater {

	private ContactUsRepository contactUsRepository;
	private ContactUsEntity contactUsEntity;
	private ContactUsStatus contactUsStatus;

	public ContactUsStatusUpdater(ContactUsRepository contactUsRepository, ContactUsEntity contactUsEntity, ContactUsStatus contactUsStatus){
		this.contactUsRepository = contactUsRepository;
		this.contactUsEntity = contactUsEntity;
		this.contactUsStatus = contactUsStatus;
	}

	@Override
	public void updateStatus() {
		Calendar calendar = Calendar.getInstance();
		contactUsEntity.setContactStatus(contactUsStatus);
		contactUsEntity.setAnswerDate(new Timestamp(calendar.getTime().getTime()));
		contactUsRepository.saveAndFlush(contactUsEntity);
	}
}

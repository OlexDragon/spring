package jk.web.workers.email;

import java.sql.Timestamp;
import java.util.Calendar;

import jk.web.entities.ContactUsEntity;
import jk.web.entities.ContactUsEntity.ContactUsStatus;
import jk.web.entities.repositories.ContactUsRepository;

public class ContactUsStatusUpdater implements StatusUpdater {

	private ContactUsRepository contactUsRepository;
	private ContactUsEntity contactUsEntity;
	private ContactUsStatus statusBefor;
	private ContactUsStatus statusAfter;

	public ContactUsStatusUpdater(ContactUsRepository contactUsRepository, ContactUsEntity contactUsEntity, ContactUsStatus statusBefor, ContactUsStatus statusAfter){
		this.contactUsRepository = contactUsRepository;
		this.contactUsEntity = contactUsEntity;
		this.statusBefor = statusBefor;
		this.statusAfter = statusAfter;
	}


	@Override
	public void preUpdateStatus() {
		contactUsEntity.setContactUsStatus(statusBefor);
		contactUsEntity.setAnswerDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		contactUsRepository.saveAndFlush(contactUsEntity);
	}

	@Override
	public void postUpdateStatus() {
		contactUsEntity.setContactUsStatus(statusAfter);
		contactUsEntity.setAnswerDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		contactUsRepository.saveAndFlush(contactUsEntity);
	}
}

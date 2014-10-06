package jk.web.workers;

import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import jk.web.user.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class EMailWorker {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private ApplicationContext applicationContext;

	private String eMailAddress;
	private String password;
	private Properties props;

	public EMailWorker(Properties props, String eMailAddress, String password) {
		logger.entry(eMailAddress, password, props);
		this.props = props;
		this.eMailAddress = eMailAddress;
		this.password = password;
	}

	public void sendRegistrationMail(User user, String url, Locale locale){
		logger.entry(user);
		new EMailThread(user, url, locale);
	}

	public void sendEMail(String eMail, String titleCode, String message) {
		new MailSender( eMail, titleCode, message);
	}

	public class MailSender extends Thread {

		private String eMail;
		private String subject;
		private String message;

		public MailSender(String eMail, String titleCode, String messageCode) {
			logger.trace("\n\t{}\n\t{}\n\t{}", eMail, titleCode, messageCode);
			this.eMail = eMail;
			this.subject = titleCode;
			this.message = messageCode;

			setPriority(Thread.MIN_PRIORITY);
			start();
		}

		@Override
		public void run() {
			for(int i=0; i<5; i++){
				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(eMailAddress, password);
					}
				});

				MimeMessage mimeMessage = new MimeMessage(session);
				try {
					mimeMessage.setFrom(new InternetAddress(eMailAddress));
					mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(eMail));
					mimeMessage.setSubject(subject, "UTF-8");

					mimeMessage.setSentDate(Calendar.getInstance().getTime());
					mimeMessage.setContent(message, "text/html; charset=UTF-8" );
					Transport.send(mimeMessage);
				} catch (MessagingException e) {
					logger.catching(e);
					try {
						Thread.sleep(3*60*80*1000);
					} catch (InterruptedException e1) {
						logger.catching(e);
					}
					continue;
				}
				break;
			}
		}
	
	}

	private class MailThreadWorker extends Thread {

		private EMailThread mailThread;

		public MailThreadWorker(EMailThread mailThread) {
			this.mailThread = mailThread;

			setPriority(Thread.MIN_PRIORITY);
			setDaemon(true);
			start();
		}

		@Override
		public void run() {
			if(mailThread!=null)
				try {
					mailThread.join();
					Thread.sleep(3*60*60*1000);
					mailThread.start();
				} catch (InterruptedException e) {
					logger.catching(e);
				}
		}
	}

	private class EMailThread extends Thread {

		private String url;
		private User user;
		private Locale locale;

		public EMailThread(User signUpForm, String url, Locale locale) {
			this.url = url;
			this.user = signUpForm;
			this.locale = locale;

			setDaemon(true);
			setPriority(Thread.MIN_PRIORITY);
			start();
		}

		@Override
		public void run() {
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(eMailAddress, password);
	            }
	        });

	        String firstName = user.getFirstName();
			String lastName = user.getLastName();

			MimeMessage message = new MimeMessage(session);
	        try {
				message.setFrom(new InternetAddress(eMailAddress));
		        String eMail = user.getEMail();
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(eMail));
				message.setSubject(getSubject(firstName, lastName, locale), "UTF-8");

				message.setContent(getMessage(	url,
												user.getUsername(),
												firstName,
												lastName,
												eMail,
												user.getNewPassword(),
												locale),
						"text/html; charset=UTF-8" );
		        Transport.send(message);
			} catch (MessagingException e) {
				logger.catching(e);
				new MailThreadWorker(this);
			}

	        logger.info("Email Send Successfully");
		}

		private String getMessage(String url, String username, String firstName, String lastName, String eMail, String password, Locale locale) {
			return logger.exit(applicationContext.getMessage(	"EMailWorker.message",
																new String[]{	firstName,
																				username,
																				password,
																				lastName,
																				url,
																				eMail},
																"Thank you",
																locale));
		}

		private String getSubject(String firstName, String lastName, Locale locale) {
			return logger.exit(applicationContext.getMessage(	"EMailWorker.subject",
																new String[]{	firstName.toUpperCase(),
																				lastName.toUpperCase()},
																				"Thank you",
																locale));
		}
	}
}

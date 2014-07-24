package jk.web.workers;

import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import jk.web.user.SignUpForm;

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

	public void sendRegistrationMail(SignUpForm signUpForm, String url, Locale locale){
		logger.entry(signUpForm);
		new EMailThread(signUpForm, url, locale);
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
		private SignUpForm signUpForm;
		private Locale locale;

		public EMailThread(SignUpForm signUpForm, String url, Locale locale) {
			this.url = url;
			this.signUpForm = signUpForm;
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

	        String firstName = signUpForm.getFirstName();
			String lastName = signUpForm.getLastName();

			Message message = new MimeMessage(session);
	        try {
				message.setFrom(new InternetAddress(eMailAddress));
		        String eMail = signUpForm.getEMail();
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(eMail));
				message.setSubject(getSubject(firstName, lastName, locale));

				message.setContent(getMessage(	url,
												signUpForm.getUsername(),
												firstName,
												lastName,
												eMail,
												signUpForm.getPassword(),
												locale),
						"text/html" );
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

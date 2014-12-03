package jk.web.workers;

import java.util.Date;
import java.util.Locale;

import javax.mail.internet.MimeMessage;

import jk.web.user.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EMailWorker {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private JavaMailSenderImpl mailSender;
	@Autowired
	private TemplateEngine templateEngine;

//	public EMailWorker(JavaMailSender javaMailSenderImpl, TemplateEngine templateEngine) {
//
//		this.javaMailSenderImpl = javaMailSenderImpl;
//		this.templateEngine = templateEngine;
//	}

	public void sendRegistrationMail(User user, String url, Locale locale, Context context){
		logger.entry(user);
		new ConfirmationEMail(user, url, locale, context);
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
//				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//					@Override
//					protected PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication(eMailAddress, password);
//					}
//				});
//
//				MimeMessage mimeMessage = new MimeMessage(session);
//				try {
//					mimeMessage.setFrom(new InternetAddress(eMailAddress));
//					mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(eMail));
//					mimeMessage.setSubject(subject, "UTF-8");
//
//					mimeMessage.setSentDate(Calendar.getInstance().getTime());
//					mimeMessage.setContent(message, "text/html; charset=UTF-8" );
//					Transport.send(mimeMessage);
//				} catch (MessagingException e) {
//					logger.catching(e);
//					try {
//						Thread.sleep(3*60*80*1000);
//					} catch (InterruptedException e1) {
//						logger.catching(e);
//					}
//					continue;
//				}
//				break;
			}
		}
	
	}

	private class MailThreadWorker extends Thread {

		private ConfirmationEMail mailThread;

		public MailThreadWorker(ConfirmationEMail mailThread) {
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

	private class ConfirmationEMail extends Thread {

		private final User user;
		private final Locale locale;
		private final Context context;

		public ConfirmationEMail(User signUpForm, String url, Locale locale, Context context) {

			this.user = signUpForm;
			this.locale = locale;
			this.context = context;
			context.setVariable("firstName", signUpForm.getFirstName());
			context.setVariable("lastName", signUpForm.getLastName());
			context.setVariable("href", url);

			setDaemon(true);
			setPriority(Thread.MIN_PRIORITY);
			start();
		}

		@Override
		public void run() {

			final String htmlContent = templateEngine.process("mails/signup_confirmation", context);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	        try {
	        	message.setFrom(mailSender.getUsername());
				message.setTo(user.getEMail());
				message.setSentDate(new Date());
				message.setSubject(getSubject(user.getFirstName(), user.getLastName(), locale));
				message.setText(htmlContent, true);
				mailSender.send(mimeMessage);
			} catch (Exception e) {
				logger.catching(e);
				new MailThreadWorker(this);
			}

	        logger.info("Email Send Successfully");
		}

		private String getSubject(String firstName, String lastName, Locale locale) {
			return logger.exit(applicationContext.getMessage(	"EMailWorker.signup.subject",
																new String[]{	firstName.toUpperCase(),
																				lastName.toUpperCase()},
																				"Thank you",
																locale));
		}
	}
}

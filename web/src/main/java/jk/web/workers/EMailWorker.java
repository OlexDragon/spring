package jk.web.workers;

import java.util.Date;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import jk.web.user.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	//Values from application.properties file
	@Value("${email.from}")
	private String emaleFrom;

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

			int priority = getPriority();
			if(priority>Thread.MIN_PRIORITY)
				setPriority(--priority);
			start();
		}

		@Override
		public void run() {
			if(eMail!=null){
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
				try {

					helper.setTo(new InternetAddress(eMail));
					helper.setFrom(new InternetAddress(emaleFrom));
					helper.setSubject(subject);
					helper.setText(message);

					mailSender.send(mimeMessage);

				} catch (MessagingException e) {
					logger.catching(e);
				}
			}else
				logger.warn("email(send to) didn't set(==null)");
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

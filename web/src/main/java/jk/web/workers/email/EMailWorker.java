package jk.web.workers.email;

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
	@Value("${jk.email.from}")
	private String emaleFrom;

	public void sendRegistrationMail(User user, String url, Locale locale, Context context){
		logger.entry(user);
		context.setVariable("firstName", user.getFirstName());
		context.setVariable("lastName", user.getLastName());
		context.setVariable("href", url);
		String subject = applicationContext.getMessage(	"EMailWorker.signup.subject",
				new String[]{	user.getFirstName().toUpperCase(),
								user.getLastName().toUpperCase()},
								"Thank you",
				locale);
		new SendEMailTemplet("mails/signup_confirmation", user.getEMail(), subject, context);
	}

	public void sendEMail(String eMail, String titleCode, String message, StatusUpdater statusUpdater) {
		new MailSender( eMail, titleCode, message, statusUpdater);
	}

	public class MailSender extends Thread {

		private String eMail;
		private String subject;
		private String message;
		private StatusUpdater statusUpdater;

		public MailSender(String eMail, String titleCode, String messageCode, StatusUpdater statusUpdater) {
			logger.trace("\n\t{}\n\t{}\n\t{}", eMail, titleCode, messageCode);
			this.eMail = eMail;
			this.subject = titleCode;
			this.message = messageCode;
			this.statusUpdater = statusUpdater;

			int priority = getPriority();
			if(priority>Thread.MIN_PRIORITY)
				setPriority(--priority);
			start();
		}

		@Override
		public void run() {

			if(statusUpdater!=null)
				statusUpdater.preUpdateStatus();

			if(eMail!=null){
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
				try {

					helper.setTo(new InternetAddress(eMail));
					helper.setFrom(new InternetAddress(emaleFrom));
					helper.setSubject(subject);
					helper.setText(message);

					mailSender.send(mimeMessage);

					if(statusUpdater!=null)
						statusUpdater.postUpdateStatus();

				} catch (MessagingException e) {
					logger.catching(e);
				}
			}else
				logger.warn("email(send to) didn't set(==null)");
		}
	}

	private class MailThreadWorker extends Thread {

		private SendEMailTemplet mailThread;

		public MailThreadWorker(SendEMailTemplet mailThread) {
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

	private class SendEMailTemplet extends Thread {

		private final Context context;
		private final String templetePath;
		private String email;
		private String subject;

		public SendEMailTemplet(String templetePath, String email, String subject, Context context) {

			this.templetePath = templetePath;
			this.email = email;
			this.subject = subject;
			this.context = context;

			setDaemon(true);
			setPriority(Thread.MIN_PRIORITY);
			start();
		}

		@Override
		public void run() {

			final String htmlContent = templateEngine.process(templetePath, context);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	        try {
	        	message.setFrom(mailSender.getUsername());
				message.setTo(email);
				message.setSentDate(new Date());
				message.setSubject(subject);
				message.setText(htmlContent, true);
				mailSender.send(mimeMessage);
			} catch (Exception e) {
				logger.catching(e);
				new MailThreadWorker(this);
			}

	        logger.info("Email Send Successfully");
		}
	}
}

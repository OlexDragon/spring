package jk.web.configuration;

import java.util.Properties;

import jk.web.workers.email.ZohoMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

@Configuration
public class EMailConfig {

	public enum ConfirmStatus{
		SENT,
		CONFIRMED,
		ERROR
	}

	@Autowired
	private TemplateEngine templateEngine;

	//Values from application.properties file
	@Value("${jk.email.username}")
	private String username;
	@Value("${jk.email.password}")
	private String password;
	@Value("${jk.mail.smtp.host}")
	private String host;

	@Bean(name="mailSender")
	public JavaMailSenderImpl mailSender(){
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		Properties javaMailProperties = null;
		switch(host){
		case "smtp.zoho.com":
			javaMailProperties = ZohoMail.props;
		}
		javaMailSender.setJavaMailProperties(javaMailProperties);

		javaMailSender.setHost(host);
		javaMailSender.setPort((Integer)javaMailProperties.get("mail.smtp.port"));
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		javaMailSender.setDefaultEncoding("UTF-8");
	
		return javaMailSender;
	}
}

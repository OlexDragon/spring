package jk.web.configuration;

import java.util.Properties;

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
	@Value("${email.username}")
	private String username;
	@Value("${email.password}")
	private String password;
	@Value("${mail.smtp.auth}")
	private String authentication;
	@Value("${mail.smtp.starttls.enable}")
	private String starttls;
	@Value("${mail.smtp.host}")
	private String host;
	@Value("${mail.smtp.port}")
	private String port;

	@Bean(name="mailSender")
	public JavaMailSenderImpl mailSender(){
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", authentication);
		javaMailProperties.put("mail.smtp.starttls.enable", starttls);
		javaMailSender.setJavaMailProperties(javaMailProperties);

		javaMailSender.setHost(host);
		javaMailSender.setPort(Integer.parseInt(port));
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		javaMailSender.setDefaultEncoding("UTF-8");
	
		return javaMailSender;
	}
}

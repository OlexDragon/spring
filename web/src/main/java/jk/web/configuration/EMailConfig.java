package jk.web.configuration;

import java.util.Properties;

import jk.web.workers.EMailWorker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EMailConfig {

	public enum ConfirmStatus{
		SENT,
		CONFIRMED,
		ERROR
	}

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

	@Bean(name="emailWorker")
	public EMailWorker getEMailWorker(){
		Properties props = new Properties();
		props.put("mail.smtp.auth", authentication);
		props.put("mail.smtp.starttls.enable", starttls);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		return new EMailWorker(props, username, password);
	}
}

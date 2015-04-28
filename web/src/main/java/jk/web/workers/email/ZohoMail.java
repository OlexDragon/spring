package jk.web.workers.email;

import java.util.Properties;

public class ZohoMail {

	public  final static Properties props = new Properties();
	static{
		props.put("mail.smtp.host", "smtp.zoho.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.startssl.enable", "true");
		props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback","false");
		props.setProperty("mail.smtp.socketFactory.port","465");
	}

	
}

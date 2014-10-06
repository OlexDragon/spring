package jk.web.configuration;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jk.web.workers.UserWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class LocaleConfig {

	private Logger logger = LogManager.getLogger();

	@Autowired
	private UserWorker userWorker;

	@Bean(name="menuLanguages")
	public Locale[] getMenuLanguages() {
		Locale[] locales = new Locale[]{Locale.ENGLISH, Locale.FRENCH, new Locale.Builder().setLanguage("ru").build()};
		logger.trace("{}", (Object[])locales);
		return locales;
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver(){

			@Override
			public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
				super.setLocale(request, response, locale);
				userWorker.setLocale(locale);
			}
		};
	}
}
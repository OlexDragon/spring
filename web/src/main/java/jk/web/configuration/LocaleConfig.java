package jk.web.configuration;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class LocaleConfig {

	private Logger logger = LogManager.getLogger();


	@Bean(name="menuLanguages")
	public Locale[] getMenuLanguages() {
		Locale[] locales = new Locale[]{Locale.ENGLISH, Locale.FRENCH, new Locale.Builder().setLanguage("ru").build()};
		logger.trace("{}", (Object[])locales);
		return locales;
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();// {
//
//			@Override
//			protected Locale determineDefaultLocale(HttpServletRequest request) {
//				String acceptLanguage = request.getHeader("Accept-Language");
//				LocaleConfig.this.logger.entry(acceptLanguage);
//				if (acceptLanguage == null || acceptLanguage.trim().isEmpty()) {
//					Locale determineDefaultLocale = super.determineDefaultLocale(request);
//					LocaleConfig.this.logger.trace(determineDefaultLocale);
//					return determineDefaultLocale;
//				}
//				Locale locale = request.getLocale();
//				LocaleConfig.this.logger.trace(locale);
//				return locale;
//			}
//
//			@Override
//			public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
//				if (locale != null) {
//					// Set request attribute and add cookie.
//					request.setAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME, locale);
//					addCookie(request, response, locale.toString());
//				} else {
//					// Set request attribute to fallback locale and remove
//					// cookie.
//					request.setAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME, determineDefaultLocale(request));
//					removeCookie(response);
//				}
//			}
//
//			public void addCookie(HttpServletRequest request, HttpServletResponse response, String cookieValue) {
//				Cookie cookie = createCookie(request, cookieValue);
//				Integer maxAge = getCookieMaxAge();
//				if (maxAge != null) {
//					cookie.setMaxAge(maxAge);
//				}
//				if (isCookieSecure()) {
//					cookie.setSecure(true);
//				}
//				response.addCookie(cookie);
//				if (LocaleConfig.this.logger.isDebugEnabled())
//					LocaleConfig.this.logger.debug("Added cookie with name [{}] and value [{}]", getCookieName(), cookieValue);
//			}
//
//			private Cookie createCookie(HttpServletRequest request, String cookieValue) {
//		        Cookie cookie = new Cookie(getCookieName(), cookieValue);
//		        String cookieDomain = getCookieDomain();
//				if (cookieDomain != null)
//		            cookie.setDomain(cookieDomain);
//		        cookie.setPath(request.getContextPath());
//		        return cookie;
//			}
//		};
	}
}
package jk.web.configuration;

import jk.web.filters.Statistic;
import jk.web.workers.FileWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true)
public class WebConfig extends WebMvcConfigurerAdapter {

	private final Logger logger = LogManager.getLogger();

	@Value("${jk.resources.path}")
	private String filesPath;

	@Value("${google.apiKey}")
	private String googleApiKey;

	@Value("${google.map.size}")
	private String googleMapSize;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		return localeChangeInterceptor;
	}

	@Bean
	public FileWorker fileWorker(){
		return new FileWorker(filesPath, googleApiKey, googleMapSize);
	}

	public final static String MAPES_RESOURCE = "/maps/";
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		logger.trace("\n\tresourcePath =\t{}", filesPath);
		registry.addResourceHandler(FileWorker.RESOURCE_HANDLER + "/**").addResourceLocations("file:"+filesPath);
	}

	@Bean
	public OncePerRequestFilter statistic(){
		return new Statistic();
	}
}

package jk.web.configuration;

import java.io.File;

import jk.web.workers.FileWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	private final Logger logger = LogManager.getLogger();

	@Value("${files.path}")
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
		File f = new File(filesPath, FileWorker.MAPS_RELATIVE_PATH+"**");
		String mapsPth = f.getAbsolutePath();
		logger.trace("\n\tmapsPth =\t{}", mapsPth);
		registry.addResourceHandler(MAPES_RESOURCE+"**").addResourceLocations("file:"+mapsPth);
	}
}

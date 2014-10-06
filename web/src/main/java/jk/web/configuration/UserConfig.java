package jk.web.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import jk.web.user.repository.CountryRepository;
import jk.web.user.services.SimpleSocialUsersDetailService;
import jk.web.user.validators.SignUpFormValidator;
import jk.web.workers.AddressWorker;
import jk.web.workers.UserWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.security.SocialUserDetailsService;

@Configuration
public class UserConfig {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private UserDetailsService userDetailsService;

	@Value("${country.list}")
	private String countriesCSV;

	@Value("${user.username.range}")
	private String usernameRange;

	@Value("${user.password.range}")
	private String passwordRange;

	@Bean
	public UserWorker getUserWorker(){
		logger.entry();
		return new UserWorker();
	}

	@Bean
	public SignUpFormValidator getSignUpFormValidator(){
		logger.entry();
		return new SignUpFormValidator(usernameRange(), passwordRange());
	}

	@Bean
	public AddressWorker getAddressWorker(){
		logger.entry(countriesCSV);

		String[] countries = null;

		if(countriesCSV!=null)
			countries = countriesCSV.trim().split("\\s*,\\s*");

		return new AddressWorker(countryRepository, new HashSet<String>(Arrays.asList(countries)));
	}

    @Bean
    public SocialUserDetailsService socialUsersDetailService() {
        return new SimpleSocialUsersDetailService(userDetailsService);
    }

    @Bean(name="usernameRange")
    public Map<String, Integer> usernameRange(){
    	logger.entry(usernameRange);
    	return getRange(usernameRange);
    }

    @Bean(name="passwordRange")
    public Map<String, Integer> passwordRange(){
    	logger.entry(passwordRange);
    	return getRange(passwordRange);
    }

    public static Map<String, Integer> getRange(String rangeStr){
    	Integer[] range = new Integer[]{1, 64};

    	if(rangeStr!=null){
    		String[] split = rangeStr.split(" ", 2);
    		for(int i=0; i<split.length; i++){
    			try{
    				range[i] = Integer.parseInt(split[i]);
    			}catch(NumberFormatException ex){}
    		}
    		if(range[0]>range[1]){
    			int tmp = range[0];
    			range[0] = range[1];
    			range[1] = tmp;
    		}
    	}
    	Map<String, Integer> rangeMap = new HashMap<String, Integer>();
    	rangeMap.put("min", range[0]);
    	rangeMap.put("max", range[1]);
    	return rangeMap;
    }
}

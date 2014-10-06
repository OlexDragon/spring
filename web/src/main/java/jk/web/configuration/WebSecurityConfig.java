package jk.web.configuration;

import jk.web.user.listeners.LoginListener;
import jk.web.user.repository.LoginRepository;
import jk.web.user.services.LoginDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginRepository loginRepository;

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService())
//        .passwordEncoder(passwordEncoder());
//	}
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    // @formatter:off
	    auth
	        .userDetailsService(userDetailsService())
	        .passwordEncoder(passwordEncoder())
	    ;
	    // @formatter:on
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(
				"/",
				"/home",
				"/login/**",
				"/connect/**",
				"/signup",
				"/confirm/**",
				"/css/**",
				"/js/**",
				"/images/**",
				"/**/favicon.ico")
		.permitAll().anyRequest().authenticated();

		http.formLogin().loginPage("/login").permitAll()
		.and()
			.rememberMe();
		http.authorizeRequests().antMatchers("*.css").permitAll();
		http.csrf().disable();
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new LoginDetailsServiceImpl(loginRepository);
    }

    @Bean
    public LoginListener getLoginListener(){
		return new LoginListener();
    }
}

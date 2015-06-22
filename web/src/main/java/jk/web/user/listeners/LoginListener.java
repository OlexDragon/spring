package jk.web.user.listeners;

import java.sql.Timestamp;
import java.util.Calendar;

import jk.web.entities.user.LoginEntity;
import jk.web.workers.UserWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private UserWorker userWorker;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
	      String username = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();
	      LoginEntity loginEntity = userWorker.getLoginEntity(username);
	      loginEntity.setLastAccessed(new Timestamp(Calendar.getInstance().getTime().getTime()));
	      userWorker.save(loginEntity);
	}

}

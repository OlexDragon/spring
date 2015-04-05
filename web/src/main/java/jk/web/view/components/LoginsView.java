package jk.web.view.components;

import java.util.List;

import jk.web.entities.user.LoginEntity;
import jk.web.repositories.user.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION, proxyMode=ScopedProxyMode.TARGET_CLASS)
public class LoginsView {

	@Autowired
	private LoginRepository loginRepository;

	public List<LoginEntity> loginEntities;

	public List<LoginEntity> getAll(){
		if(loginEntities==null)
			loginEntities = loginRepository.findAll();
		return loginEntities;
	}
}

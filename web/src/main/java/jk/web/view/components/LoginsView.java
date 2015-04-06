package jk.web.view.components;

import java.util.List;
import java.util.Set;

import jk.web.entities.user.LoginEntity;
import jk.web.entities.user.LoginEntity.Permission;
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

	public String getPermissions(Long permissions){
		Set<Permission> set = Permission.toPermissions(permissions);
		StringBuilder result = new StringBuilder();
		if(set!=null)
			for(Permission p:set){
				if(result.length()!=0)
					result.append(" ");
				result.append(p);
			}
		return result.length()!=0 ? result.toString() : null;
	}

	public LoginEntity gerLogin(long id) {
		return loginRepository.findOne(id);
	}

	public LoginEntity save(LoginEntity loginEntity) {
		loginEntities=null;
		return loginRepository.save(loginEntity);
	}
}

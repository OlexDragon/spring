package jk.web.user.social;

import java.util.Iterator;

import jk.web.entities.user.social.SocialEntity;
import jk.web.workers.UserWorker;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.context.request.WebRequest;

public class FacebookInterceptor extends SocialInterceptor<Facebook>{

	public FacebookInterceptor(UserWorker userWorker) {
		super(userWorker);
	}

	@Override
	public void postConnect(Connection<Facebook> connection, WebRequest request) {
		logger.entry();

		try{
		ConnectionKey key = connection.getKey();
		logger.trace("\n\tkey = {}\n\tuserWorker = {}", key);
		SocialEntity socialEntity = userWorker.getSocialEntity(key);

		StringBuilder sb = new StringBuilder();
		sb.append("\n\t");
		sb.append("request.getRemoteUser()");
		sb.append("=\t");
		sb.append(request.getRemoteUser());
		sb.append("\n\t");
		sb.append("request.getUserPrincipal()");
		sb.append("=\t");
		sb.append(request.getUserPrincipal());
		logger.trace(sb);

		sb.append("\nParameters:");
		Iterator<String> parameterNames = request.getParameterNames();
		while(parameterNames.hasNext()){
			String pn = parameterNames.next();
			sb.append("\n\t");
			sb.append(pn);
			sb.append("=\t");
			sb.append(request.getParameter(pn));
		}
		logger.trace(sb);

		sb.append("\nHeaders:");

		Iterator<String> headerNames = request.getHeaderNames();
		while(headerNames.hasNext()){
			String hn = headerNames.next();
			sb.append("\n\t");
			sb.append(hn);
			sb.append("=\t");
			sb.append(request.getHeader(hn));
		}
		logger.trace(sb);

		sb.append("\n\n\t");
		sb.append("connection.getDisplayName()");
		sb.append("=\t");
		sb.append(connection.getDisplayName());
		sb.append("\n\t");
		sb.append("connection.getImageUrl()");
		sb.append("=\t");
		sb.append(connection.getImageUrl());
		sb.append("\n\t");
		sb.append("connection.getProfileUrl()");
		sb.append("=\t");
		sb.append(connection.getProfileUrl());
		sb.append("\n\t");
		sb.append("connection.getApi()");
		sb.append("=\t");
		sb.append(connection.getApi());
		sb.append("\n\t");
		sb.append("connection.getKey()");
		sb.append("=\t");
		sb.append(key);
		logger.trace(sb);


		UserProfile userProfile = connection.fetchUserProfile();
		sb.append("\n\n\t");
		sb.append("userProfile.getName()");
		sb.append("=\t");
		sb.append(userProfile.getName());
		sb.append("\n\t");
		sb.append("userProfile.getUsername()");
		sb.append("=\t");
		sb.append(userProfile.getUsername());
		sb.append("\n\t");
		sb.append("userProfile.getFirstName()");
		sb.append("=\t");
		sb.append(userProfile.getFirstName());
		sb.append("\n\t");
		sb.append("userProfile.getLastName()");
		sb.append("=\t");
		sb.append(userProfile.getLastName());
		sb.append("\n\t");
		sb.append("userProfile.getEmail()");
		sb.append("=\t");
		sb.append(userProfile.getEmail());
		sb.append("\n");
		logger.trace(sb);

		sb.append("\n"+"WebRequest.SCOPE_GLOBAL_SESSION");
		for(String an:request.getAttributeNames(WebRequest.SCOPE_GLOBAL_SESSION)){
			sb.append("\n\t");
			sb.append(an);
			sb.append("=\t");
			sb.append(request.getAttribute(an, WebRequest.SCOPE_GLOBAL_SESSION));
		}
		sb.append("\n");
		logger.trace(sb);

		sb.append("\n"+"WebRequest.SCOPE_REQUEST");
		for(String an:request.getAttributeNames(WebRequest.SCOPE_REQUEST)){
			sb.append("\n\t");
			sb.append(an);
			sb.append("=\t");
			sb.append(request.getAttribute(an, WebRequest.SCOPE_REQUEST));
		}
		sb.append("\n");
		logger.trace(sb);

		sb.append("\n"+"WebRequest.SCOPE_SESSION");
		for(String an:request.getAttributeNames(WebRequest.SCOPE_SESSION)){
			sb.append("\n\t");
			sb.append(an);
			sb.append("=\t");
			sb.append(request.getAttribute(an, WebRequest.SCOPE_SESSION));
		}
		logger.trace("{}", sb);
		}catch(Exception ex){
			logger.catching(ex);
		}
	}

}

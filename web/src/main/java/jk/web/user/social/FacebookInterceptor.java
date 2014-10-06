package jk.web.user.social;

import java.util.Iterator;

import jk.web.user.entities.SocialEntity;
import jk.web.workers.UserWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.SocialException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.context.request.WebRequest;

public class FacebookInterceptor extends SocialInterceptor<Facebook>{

	@Override
	public void postConnect(Connection<Facebook> connection, WebRequest request) {

		SocialEntity socialEntity = userWorker.getSocialEntity(connection.getKey());

		StringBuilder sb = new StringBuilder();
		sb.append("\n\t");
		sb.append("request.getRemoteUser()");
		sb.append("=\t");
		sb.append(request.getRemoteUser());
		sb.append("\n\t");
		sb.append("request.getUserPrincipal()");
		sb.append("=\t");
		sb.append(request.getUserPrincipal());
		sb.append("\nParameters:");
		Iterator<String> parameterNames = request.getParameterNames();
		while(parameterNames.hasNext()){
			String pn = parameterNames.next();
			sb.append("\n\t");
			sb.append(pn);
			sb.append("=\t");
			sb.append(request.getParameter(pn));
		}
		sb.append("\nHeaders:");

		Iterator<String> headerNames = request.getHeaderNames();
		while(headerNames.hasNext()){
			String hn = headerNames.next();
			sb.append("\n\t");
			sb.append(hn);
			sb.append("=\t");
			sb.append(request.getHeader(hn));
		}
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
		sb.append(connection.getKey());

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
		sb.append("\n"+"WebRequest.SCOPE_GLOBAL_SESSION");
		for(String an:request.getAttributeNames(WebRequest.SCOPE_GLOBAL_SESSION)){
			sb.append("\n\t");
			sb.append(an);
			sb.append("=\t");
			sb.append(request.getAttribute(an, WebRequest.SCOPE_GLOBAL_SESSION));
		}
		sb.append("\n");
		sb.append("\n"+"WebRequest.SCOPE_REQUEST");
		for(String an:request.getAttributeNames(WebRequest.SCOPE_REQUEST)){
			sb.append("\n\t");
			sb.append(an);
			sb.append("=\t");
			sb.append(request.getAttribute(an, WebRequest.SCOPE_REQUEST));
		}
		sb.append("\n");
		sb.append("\n"+"WebRequest.SCOPE_SESSION");
		for(String an:request.getAttributeNames(WebRequest.SCOPE_SESSION)){
			sb.append("\n\t");
			sb.append(an);
			sb.append("=\t");
			sb.append(request.getAttribute(an, WebRequest.SCOPE_SESSION));
		}
		logger.trace("{}", sb);
	}

}

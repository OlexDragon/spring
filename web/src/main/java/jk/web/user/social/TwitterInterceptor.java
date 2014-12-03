package jk.web.user.social;

import java.util.Iterator;

import jk.web.workers.UserWorker;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.context.request.WebRequest;

public class TwitterInterceptor extends SocialInterceptor<Twitter>{

	public TwitterInterceptor(UserWorker userWorker) {
		super(userWorker);
	}

	@Override
	public void postConnect(Connection<Twitter> connection, WebRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\t");
		sb.append(request.getRemoteUser());
		sb.append("\n");

		Iterator<String> headerNames = request.getHeaderNames();
		while(headerNames.hasNext()){
			String hn = headerNames.next();
			sb.append("\n\t");
			sb.append(hn);
			sb.append("\t");
			sb.append(request.getHeader(hn));
		}
		sb.append("\n\n\t");
		sb.append(connection.getDisplayName());
		sb.append("\n\t");
		sb.append(connection.getImageUrl());
		sb.append("\n\t");
		sb.append(connection.getProfileUrl());
		sb.append("\n\t");
		sb.append(connection.getApi());
		sb.append("\n\t");
		sb.append(connection.getKey());

		UserProfile userProfile = connection.fetchUserProfile();
		sb.append("\n\n\t");
		sb.append(userProfile.getName());
		sb.append("\n\t");
		sb.append(userProfile.getUsername());
		sb.append("\n\t");
		sb.append(userProfile.getFirstName());
		sb.append("\n\t");
		sb.append(userProfile.getLastName());
		sb.append("\n\t");
		sb.append(userProfile.getEmail());
		logger.trace("{}", sb);
		
	}

}

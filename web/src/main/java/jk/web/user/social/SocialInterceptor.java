package jk.web.user.social;

import jk.web.workers.UserWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

public abstract class SocialInterceptor<S>  implements ConnectInterceptor<S>{

	protected Logger logger = LogManager.getLogger(getClass());

	@Autowired
	protected UserWorker userWorker;

	@Override
	public void preConnect(ConnectionFactory<S> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {
	}
}

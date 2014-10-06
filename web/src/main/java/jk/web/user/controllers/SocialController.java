package jk.web.user.controllers;

import javax.inject.Inject;

import jk.web.user.social.FacebookInterceptor;
import jk.web.user.social.TwitterInterceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/social")
public class SocialController {

	private final Logger logger = LogManager.getLogger();

	private ConnectionRepository connectionRepository;
	private Facebook facebook;
	private Twitter twitter;
	private LinkedIn linkedIn;

	@Inject
	public SocialController(ConnectController connectController, ConnectionRepository connectionRepository, Facebook facebook, Twitter twitter, LinkedIn linkedIn) {
		this.connectionRepository = connectionRepository;
		this.facebook = facebook;
		this.twitter = twitter;
		this.linkedIn = linkedIn;
		connectController.addInterceptor(new FacebookInterceptor());
		connectController.addInterceptor(new TwitterInterceptor());
	}

	@RequestMapping
	public String social(){
		logger.entry();
		return "home";
	}

	@RequestMapping("status")
	public String status(){
		logger.entry();
		return "home";
	}

	@RequestMapping("facebookConnected")
	public String facebookLogin(){
		logger.entry();
	       if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
	            return "redirect:/connect/facebook?scope=read_stream";
	        }
		return "home";
	}

	@RequestMapping("linkedin")
	public String linkedinLogin(){
		logger.entry();
	       if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
	            return "redirect:/connect/linkedin";
	        }
		return "home";
	}

	@RequestMapping("twitterConnected")
	public String twitterLogin(){
		logger.entry();
	       if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
	            return "redirect:/connect/twitter";
	        }
		return "home";
	}
}

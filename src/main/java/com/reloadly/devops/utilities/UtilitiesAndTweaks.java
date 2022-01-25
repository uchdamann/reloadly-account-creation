package com.reloadly.devops.utilities;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reloadly.devops.exceptions.AppException;
import com.reloadly.devops.models.User;
import com.reloadly.devops.repositories.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UtilitiesAndTweaks {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private AppProperties props;

	public void channelCodeHandler(HttpServletRequest req) {
		log.info("---->>> Initializing request source check");
		log.info("---->>> Authenticated user is: {}",
				req.getUserPrincipal() == null ? "No user" : req.getUserPrincipal().getName());

		final String channelCode = req.getHeader("ChannelCode");
		final String webRequest = props.getWebChannelCode();
		final String mobileConReq = props.getMobileConsumerChannelCode();

		if (channelCode != null) {
			if (channelCode.equals(webRequest)) {
				log.info("---->>> Request source: WEB BROWSER");
			} else if (channelCode.equals(mobileConReq)) {
				log.info("---->>> Request source:  MOBILE APP - CONSUMERS");
			} else {
				log.info("---->>> Error:  Invalid access code");
				throw new AppException("Access code is invalid"); // Access code is my ChannelCode
			}
		} 
//		else {
//			log.info("---->>> Error:  Access code cannot be empty, blank or null");
//			throw new AppException("Access code cannot be empty");
//		}
	}

	public User checkAuthentication(HttpServletRequest req) {
		log.info("---->>> 1. Channel code check");
		channelCodeHandler(req); // run channel code check
		log.info("---->>> 2. Authenticaed user check");

		User user = null;
		String username = null;
		log.info("---->>> Checking request for authenticated principal via provided JWT token");

		if (req.getUserPrincipal() == null) {
			throw new AppException("No authenticated principal found");
		}

		username = req.getUserPrincipal().getName();

		if (username == null) {
			throw new AppException("No authenticated user found");
		}

		user = userRepo.findByUsername(username).orElseThrow(() -> new AppException("No authenticated user found"));

		return user;
	}
	
}
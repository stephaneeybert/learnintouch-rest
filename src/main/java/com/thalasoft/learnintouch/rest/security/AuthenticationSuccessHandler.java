package com.thalasoft.learnintouch.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {

	private static Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		logger.debug("=================>>> AUTHENTICATION SUCCESS");
		logger.debug(event.getAuthentication().toString());
	}

}

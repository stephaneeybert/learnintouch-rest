package com.thalasoft.learnintouch.rest.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TokenAuthenticationService {

	public void addTokenToResponseHeader(HttpHeaders headers, String username);

	public void addTokenToResponseHeader_NOT_USED(HttpServletResponse response, Authentication authentication);
	
	public Authentication authenticateFromToken(HttpServletRequest request);
	
}

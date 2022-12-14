package com.thalasoft.learnintouch.rest.security;

import com.thalasoft.learnintouch.data.jpa.domain.EmailAddress;
import com.thalasoft.learnintouch.data.jpa.domain.UserAccount;
import com.thalasoft.learnintouch.rest.service.CredentialsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private CredentialsService credentialsService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	if (username != null && !username.isEmpty()) {
	        UserAccount userAccount = credentialsService.findByEmail(new EmailAddress(username));
	        if (userAccount != null) {
	        	return new UserDetailsWrapper(userAccount);
	        }
    	}
    	throw new UsernameNotFoundException("The user " + username + " was not found.");
    }

}

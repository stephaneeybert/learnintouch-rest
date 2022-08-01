package com.thalasoft.learnintouch.rest.service;

import org.springframework.transaction.annotation.Transactional;

import com.thalasoft.learnintouch.data.jpa.domain.EmailAddress;
import com.thalasoft.learnintouch.data.jpa.domain.UserAccount;
import com.thalasoft.learnintouch.rest.service.resource.CredentialsResource;

@Transactional
public interface CredentialsService {

	public UserAccount findByEmail(EmailAddress email);

	public boolean checkPassword(UserAccount user, String password);
	
	public UserAccount checkPassword(CredentialsResource credentialsResource);
	
	public UserAccount updatePassword(Long id, String password);
	
}

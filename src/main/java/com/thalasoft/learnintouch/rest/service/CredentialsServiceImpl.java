package com.thalasoft.learnintouch.rest.service;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.thalasoft.learnintouch.data.exception.EntityNotFoundException;
import com.thalasoft.learnintouch.data.jpa.domain.EmailAddress;
import com.thalasoft.learnintouch.data.jpa.domain.UserAccount;
import com.thalasoft.learnintouch.data.service.jpa.UserAccountService;
import com.thalasoft.learnintouch.rest.exception.CannotEncodePasswordException;
import com.thalasoft.learnintouch.rest.service.resource.CredentialsResource;
import com.thalasoft.toolbox.utils.Common;
import com.thalasoft.toolbox.utils.Security;

@Service
public class CredentialsServiceImpl implements CredentialsService {

    private static Logger logger = LoggerFactory.getLogger(CredentialsServiceImpl.class);

    private static final int ADMIN_PASSWORD_SALT_LENGTH = 30;

	@Autowired
	UserAccountService userAccountService;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private SimpleMailMessage simpleMailMessage;
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public UserAccount findByEmail(EmailAddress email) {
		return userAccountService.findByEmail(email.toString());
	}

	@Override
    public UserAccount checkPassword(CredentialsResource credentialsResource) throws BadCredentialsException, EntityNotFoundException {
        logger.debug("===============>> In UserAccount entity check password");
        UserAccount userAccount = userAccountService.findByEmail(credentialsResource.getEmail());
		if (userAccount != null) {
			if (checkPassword(userAccount, credentialsResource.getPassword())) {
				return userAccount;
			} else {
                throw new BadCredentialsException("The user with email: " + credentialsResource.getEmail() + " and password could not match.");            	
			}
		} else {
			throw new EntityNotFoundException("The user with email: " + credentialsResource.getEmail() + " was not found.");            	
		}
    }

	@Override
	public boolean checkPassword(UserAccount userAccount, String password) {
		String givenPassword = encodePassword(userAccount.getEmail().toString(), password, userAccount.getPasswordSalt());
		logger.debug("===============>> Stored: " + userAccount.getPassword() + " Given: " + givenPassword);
		return userAccount.getPassword().equals(givenPassword);
	}
	
    @Override
    public UserAccount updatePassword(Long id, String password) throws EntityNotFoundException {
		UserAccount foundUserAccount = userAccountService.findById(id);
		if (foundUserAccount != null) {
			foundUserAccount.setReadablePassword(password);
			String passwordSalt = generatePasswordSalt();
			foundUserAccount.setPasswordSalt(passwordSalt);
			foundUserAccount.setPassword(encodePassword(foundUserAccount.getEmail().toString(), password, passwordSalt));
			
			if (javaMailSender != null) {
				simpleMailMessage.setTo(foundUserAccount.getEmail().toString());
				simpleMailMessage.setSubject(localizeErrorMessage("user.mail.update.password.subject", new Object[] { foundUserAccount.getFirstname() + " " + foundUserAccount.getLastname() }));
				simpleMailMessage.setText(localizeErrorMessage("user.mail.update.password.body", new Object[] { foundUserAccount.getFirstname() + " " + foundUserAccount.getLastname(), password }));
				try {
					javaMailSender.send(simpleMailMessage);
				} catch (MailException e) {
					System.err.println(e.getMessage());
				}
			}

			return foundUserAccount;
		} else {
			throw new EntityNotFoundException("The user with id: " + id + " was not found.");            	
		}
    }

	private String encodePassword(String email, String password, String passwordSalt) {
		String encodedPassword = null;
		try {
			encodedPassword = new String(Security.encodeBase64(email, saltPassword(password, passwordSalt)), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new CannotEncodePasswordException();
		}
		return encodedPassword;
	}

    private String generatePasswordSalt() {
        return Common.generateUniqueId(ADMIN_PASSWORD_SALT_LENGTH);
    }
    
    private String saltPassword(String password, String salt) {
        return password + salt;
    }

	private String localizeErrorMessage(String errorCode, Object args[]) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(errorCode, args, locale);
	}
	
}

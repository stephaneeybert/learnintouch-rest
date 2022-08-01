package com.thalasoft.learnintouch.rest.security;

import com.thalasoft.learnintouch.data.jpa.domain.EmailAddress;
import com.thalasoft.learnintouch.data.jpa.domain.UserAccount;
import com.thalasoft.learnintouch.rest.service.CredentialsService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CredentialsService credentialsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        UserAccount userAccount = null;
        try {
        	userAccount = credentialsService.findByEmail(new EmailAddress(email));
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("The login " + email + " and password could not match.");            	
        }
        if (userAccount != null) {
            if (credentialsService.checkPassword(userAccount, password)) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                return new UsernamePasswordAuthenticationToken(email, password, grantedAuthorities);
            } else {
                throw new BadCredentialsException("The login " + userAccount.getEmail() + " and password could not match.");            	
            }
        }
        throw new BadCredentialsException("The login " + authentication.getPrincipal() + " and password could not match.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
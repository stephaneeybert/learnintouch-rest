package com.thalasoft.learnintouch.rest.security;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.thalasoft.learnintouch.data.jpa.domain.UserAccount;
import com.thalasoft.learnintouch.data.jpa.domain.UserRole;

@SuppressWarnings("serial")
public class UserDetailsWrapper implements UserDetails {

	private UserAccount userAccount;

	public UserDetailsWrapper(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	public UserAccount getUserAccount() {
		return userAccount;
	}

	@Override
	public String getUsername() {
		return userAccount.getEmail().toString();
	}

	@Override
	public String getPassword() {
		return userAccount.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (UserRole userRole : userAccount.getUserRoles()) {
        	grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRole()));
        }
		return grantedAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO user.isAccountNonExpired()
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO user.isAccountNonLocked()
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO user.isCredentialsNonExpired()
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO user.isEnabled()
		return true;
	}

}

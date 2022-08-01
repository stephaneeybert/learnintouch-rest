package com.thalasoft.learnintouch.rest.assertion;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;

import com.thalasoft.learnintouch.rest.resource.AdminResource;

public class AdminResourceAssert extends AbstractAssert<AdminResourceAssert, AdminResource> {

	private AdminResourceAssert(AdminResource actual) {
		super(actual, AdminResourceAssert.class);
	}
	
	public static AdminResourceAssert assertThatAdminResource(AdminResource actual) {
		return new AdminResourceAssert(actual);
	}

	public AdminResourceAssert hasEmail(String email) {
		isNotNull();
		assertThat(actual.getEmail()).overridingErrorMessage("Expected the email to be <%s> but was <%s>.", email, actual.getEmail()).isEqualTo(email);
		return this;
	}
	
	public AdminResourceAssert hasLogin(String login) {
		isNotNull();
		assertThat(actual.getLogin()).overridingErrorMessage("Expected the login to be <%s> but was <%s>.", login, actual.getLogin()).isEqualTo(login);
		return this;
	}
	
	public AdminResourceAssert hasPassword(String password) {
		isNotNull();
		assertThat(actual.getEmail()).overridingErrorMessage("Expected the password to be <%s> but was <%s>.", password, actual.getPassword()).isEqualTo(password);
		return this;
	}
	
	public AdminResourceAssert isSameAs(AdminResource admin) {
		isNotNull();
		assertThat(actual.hashCode()).overridingErrorMessage("Expected the hash code to be <%s> but was <%s>.", admin.hashCode(), actual.hashCode()).isEqualTo(admin.hashCode());
		return this;
	}
	
	public AdminResourceAssert exists() {
		isNotNull();
		assertThat(actual).overridingErrorMessage("Expected the admin to exist but it didn't.").isNotNull();
		return this;
	}
	
	public AdminResourceAssert doesNotExist() {
		isNull();
		assertThat(actual).overridingErrorMessage("Expected the admin not to exist but it did.").isNull();
		return this;
	}
	
}

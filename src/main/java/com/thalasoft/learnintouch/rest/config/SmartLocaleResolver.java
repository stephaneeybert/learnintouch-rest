package com.thalasoft.learnintouch.rest.config;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

public class SmartLocaleResolver extends CookieLocaleResolver {

    private static Logger logger = LoggerFactory.getLogger(SmartLocaleResolver.class);

	private AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
	
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Locale locale = super.determineDefaultLocale(request);
		if (null == locale) {
	        locale = acceptHeaderLocaleResolver.resolveLocale(request);
		}
		return locale;
	}

}

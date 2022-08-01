package com.thalasoft.learnintouch.rest.config;

import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.thalasoft.learnintouch.rest.utils.CommonConstants;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

	private static Logger logger = LoggerFactory.getLogger(WebInit.class);

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setInitParameter("dispatchOptionsRequest", "true");
		registration.setAsyncSupported(true);
		registration.setLoadOnStartup(1);
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { ApplicationConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		// The '/api/*' prefix is to allow a match of an .../api/... request to a controller mapping
		// The '/' is to allow a match of a root request to a static resource
		return new String[] { "/api/*", "/" };
	}

	@Override
	protected String getServletName() {
		return CommonConstants.SERVLET_NAME;
	}
	
}

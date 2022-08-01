package com.thalasoft.learnintouch.rest.config;

import com.thalasoft.learnintouch.data.condition.EnvAcceptance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnvAcceptance
@Configuration
@PropertySource({ "classpath:application-acceptance.properties" })
public class ApplicationAcceptanceProperties extends AbstractApplicationProperties {

	private static Logger logger = LoggerFactory.getLogger(ApplicationAcceptanceProperties.class);

	public ApplicationAcceptanceProperties() {
		logger.debug("===========>> Loading the classpath application-acceptance.properties file");
	}

}

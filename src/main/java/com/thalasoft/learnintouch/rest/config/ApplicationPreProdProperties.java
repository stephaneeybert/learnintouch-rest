package com.thalasoft.learnintouch.rest.config;

import com.thalasoft.learnintouch.data.condition.EnvPreProd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnvPreProd
@Configuration
@PropertySource({ "classpath:application-preprod.properties" })
public class ApplicationPreProdProperties extends AbstractApplicationProperties {

	private static Logger logger = LoggerFactory.getLogger(ApplicationPreProdProperties.class);

	public ApplicationPreProdProperties() {
		logger.debug("===========>> Loading the classpath application-preprod.properties file");
	}

}

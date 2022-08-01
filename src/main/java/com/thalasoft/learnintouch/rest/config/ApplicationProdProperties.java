package com.thalasoft.learnintouch.rest.config;

import com.thalasoft.learnintouch.data.condition.EnvProd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnvProd
@Configuration
@PropertySource({ "classpath:application-prod.properties" })
public class ApplicationProdProperties extends AbstractApplicationProperties {

	private static Logger logger = LoggerFactory.getLogger(ApplicationProdProperties.class);

	public ApplicationProdProperties() {
        logger.debug("===========>> Loading the classpath application-prod.properties file");
	}

}

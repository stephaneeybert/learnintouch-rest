package com.thalasoft.learnintouch.rest.config;

import com.thalasoft.learnintouch.data.condition.EnvTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnvTest
@Configuration
@PropertySource({ "classpath:application-test.properties" })
public class ApplicationTestProperties extends AbstractApplicationProperties {
    private static Logger logger = LoggerFactory.getLogger(ApplicationProdProperties.class);

    public ApplicationTestProperties() {
        logger.debug("===========>> Loading the classpath application-test.properties file");
    }

}

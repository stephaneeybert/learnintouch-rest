package com.thalasoft.learnintouch.rest.config;

import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractApplicationProperties implements ApplicationProperties {

    @Value("${" + PropertyNames.CONFIG_AUTH_TOKEN_PRIVATE_KEY + "}")
	private String authenticationTokenPrivateKey;
	
    @Value("${" + PropertyNames.CONFIG_MAIL_HOST + "}")
    private String host;

    @Value("${" + PropertyNames.CONFIG_MAIL_PORT + "}")
    private String port;

    @Value("${" + PropertyNames.CONFIG_MAIL_PROTOCOL + "}")
    private String protocol;

    @Value("${" + PropertyNames.CONFIG_MAIL_USERNAME + "}")
    private String username;

    @Value("${" + PropertyNames.CONFIG_MAIL_PASSWORD + "}")
    private String password;

    @Value("${" + PropertyNames.CONFIG_MAIL_FROM + "}")
    private String mailFrom;
    
    public String getAuthenticationTokenPrivateKey() {
        return authenticationTokenPrivateKey;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public String getPassword() {
        return password;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUsername() {
        return username;
    }

}

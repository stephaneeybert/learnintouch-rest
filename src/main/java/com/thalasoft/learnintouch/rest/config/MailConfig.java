package com.thalasoft.learnintouch.rest.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public JavaMailSender javaMailSender() {
    	String host = applicationProperties.getHost();
    	String port = applicationProperties.getPort();
    	String protocol = applicationProperties.getProtocol();
    	String username = applicationProperties.getUsername();
    	String password = applicationProperties.getPassword();
    	if (!password.isEmpty() && !username.isEmpty() && !protocol.isEmpty() && !port.isEmpty() && !host.isEmpty()) {
	        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
	        javaMailSender.setHost(host);
	        javaMailSender.setPort(Integer.parseInt(port));
	        javaMailSender.setProtocol(protocol);
	        javaMailSender.setUsername(username);
	        javaMailSender.setPassword(password);
	        javaMailSender.setJavaMailProperties(getMailProperties());
	        return javaMailSender;
    	} else {
    		return null;
    	}
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() {
    	SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    	simpleMailMessage.setFrom(applicationProperties.getMailFrom());
        return simpleMailMessage;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "false");
        properties.setProperty("mail.smtp.quitwait", "false");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.debug", "true");
        return properties;
    }
    
}

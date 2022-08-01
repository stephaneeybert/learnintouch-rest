package com.thalasoft.learnintouch.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.thalasoft.toolbox.spring.PackageBeanNameGenerator;

@Configuration
@ComponentScan(nameGenerator = PackageBeanNameGenerator.class, basePackages = {
		"com.thalasoft.learnintouch.data.config",
		"com.thalasoft.learnintouch.rest.config",
		"com.thalasoft.learnintouch.rest.service",
		"com.thalasoft.learnintouch.rest.bootstrap" })
public class ApplicationConfiguration {
}

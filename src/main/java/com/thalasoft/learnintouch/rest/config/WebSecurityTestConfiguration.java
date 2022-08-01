package com.thalasoft.learnintouch.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.thalasoft.learnintouch.rest.filter.SimpleCORSFilter;
import com.thalasoft.learnintouch.rest.security.RESTAuthenticationEntryPoint;
import com.thalasoft.toolbox.spring.PackageBeanNameGenerator;

@Configuration
@EnableWebSecurity
@ComponentScan(nameGenerator = PackageBeanNameGenerator.class, basePackages = { "com.thalasoft.learnintouch.rest.security" })
public class WebSecurityTestConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	RESTAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("stephane").password("mypassword").roles("ADMIN");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.httpBasic()
		.authenticationEntryPoint(restAuthenticationEntryPoint)
		.and()
		.authorizeRequests()
		.antMatchers("/**").hasRole("ADMIN")
		.anyRequest().authenticated();
	}
	
    @Bean
    public SimpleCORSFilter simpleCORSFilter() throws Exception {
        return new SimpleCORSFilter();
    }

}

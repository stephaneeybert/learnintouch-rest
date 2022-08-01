package com.thalasoft.learnintouch.rest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.thalasoft.learnintouch.data.condition.EnvNotTest;
import com.thalasoft.learnintouch.rest.filter.SimpleCORSFilter;
import com.thalasoft.learnintouch.rest.security.AuthenticationFromTokenFilter;
import com.thalasoft.learnintouch.rest.security.CustomAuthenticationProvider;
import com.thalasoft.learnintouch.rest.security.RESTAuthenticationEntryPoint;
import com.thalasoft.learnintouch.rest.utils.RESTConstants;
import com.thalasoft.learnintouch.rest.utils.UserDomainConstants;
import com.thalasoft.toolbox.spring.PackageBeanNameGenerator;

@EnvNotTest
@Configuration
@EnableWebSecurity
@ComponentScan(nameGenerator = PackageBeanNameGenerator.class, basePackages = { "com.thalasoft.learnintouch.rest.security" })
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);

	@Autowired
	private UserDetailsService userDetailsService;
	
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private AuthenticationFromTokenFilter authenticationFromTokenFilter;
    
	@Autowired
	private RESTAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
// TODO Allow non https access as the browser non signed certificate warning sucks
// Maybe someday we'll have a signed certificate...	
//		http.requiresChannel().antMatchers(RESTConstants.SLASH + RESTConstants.API + "/**").requiresSecure();
		
		logger.debug("==========>> Configuring web security");
		http.userDetailsService(userDetailsService);
		http.addFilterBefore(new SimpleCORSFilter(), UsernamePasswordAuthenticationFilter.class);		
		http.addFilterBefore(authenticationFromTokenFilter, UsernamePasswordAuthenticationFilter.class);

		http
		.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint)
		.and().authorizeRequests()
		.antMatchers("/index.html").permitAll()
		.antMatchers("/404.html").permitAll()
		.antMatchers("/favicon.ico").permitAll()
		.antMatchers("/robots.txt").permitAll()
		.antMatchers("/fonts/**").permitAll()
		.antMatchers("/modules/**").permitAll()
		.antMatchers("/scripts/**").permitAll()
		.antMatchers("/styles/**").permitAll()
        .antMatchers(RESTConstants.SLASH + RESTConstants.API + RESTConstants.SLASH + RESTConstants.META).permitAll()
		.antMatchers(RESTConstants.SLASH + RESTConstants.API + RESTConstants.SLASH + UserDomainConstants.USERS + RESTConstants.SLASH + UserDomainConstants.LOGIN).permitAll()
		.antMatchers(RESTConstants.SLASH + RESTConstants.API + "/**").hasRole("ADMIN").anyRequest().authenticated(); 

// NOT USED
//		http.addFilterBefore(new CreateAuthenticationTokenFilter(RESTConstants.SLASH + RESTConstants.API + RESTConstants.SLASH + RESTConstants.ADMINS + RESTConstants.SLASH + RESTConstants.LOGIN), UsernamePasswordAuthenticationFilter.class);
//		.and().formLogin()
//		.loginPage(RESTConstants.SLASH + RESTConstants.API + RESTConstants.SLASH + RESTConstants.ADMINS + RESTConstants.SLASH + RESTConstants.LOGIN)
//		.loginProcessingUrl(RESTConstants.SLASH + RESTConstants.API + RESTConstants.SLASH + RESTConstants.ADMINS + RESTConstants.SLASH + RESTConstants.LOGIN)
//		.defaultSuccessUrl(RESTConstants.SLASH + RESTConstants.API + RESTConstants.SLASH + "project/list")
// 		.successHandler(restSimpleUrlAuthenticationSuccessHandler)
//		.failureUrl(RESTConstants.SLASH + RESTConstants.API + RESTConstants.SLASH + RESTConstants.ADMINS + RESTConstants.SLASH + RESTConstants.LOGIN + "?error")
//		.failureHandler(restSimpleUrlAuthenticationFailureHandler)
//		.and().logout().logoutSuccessUrl(RESTConstants.SLASH + RESTConstants.API + RESTConstants.SLASH + RESTConstants.ADMINS + RESTConstants.SLASH + RESTConstants.LOGOUT + "?logout").logoutUrl("/user/logout").permitAll()
	}

//    @Bean
//    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
//    	UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
//    	usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
//    	usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(restSimpleUrlAuthenticationSuccessHandler);
//    	usernamePasswordAuthenticationFilter.setPostOnly(true);
//    	logger.debug("=============>> Configured the custom usernamePasswordAuthenticationFilter");
//    	return usernamePasswordAuthenticationFilter;
//    }
//
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//    	return super.authenticationManagerBean();
//    }

}

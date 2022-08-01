package com.thalasoft.learnintouch.rest.config;

import java.util.List;
import java.util.Locale;

import javax.servlet.Filter;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.thalasoft.toolbox.spring.PackageBeanNameGenerator;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(nameGenerator = PackageBeanNameGenerator.class, basePackages = {
		"com.thalasoft.learnintouch.rest.exception",
		"com.thalasoft.learnintouch.rest.controller",
		"com.thalasoft.learnintouch.rest.assembler" })
public class WebConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setFallbackPageable(new PageRequest(0, 10));
		// resolver.setPageParameterName("page");
		// resolver.setSizeParameterName("size");
		// resolver.setOneIndexedParameters(true);
		argumentResolvers.add(resolver);
		super.addArgumentResolvers(argumentResolvers);
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages/messages",
				"classpath:messages/validation");
		// If true, the key of the message will be displayed if the key is not
		// found, instead of throwing an exception
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		// The value 0 means always reload the messages to be developer friendly
		messageSource.setCacheSeconds(0);
		return messageSource;
	}

	// The locale interceptor provides a way to switch the language in any page
	// just by passing the lang=’en’, lang=’fr’, and so on to the url
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
	}

	@Bean
	public LocaleResolver localeResolver() {
		// AcceptHeaderLocaleResolver localeResolver = new
		// AcceptHeaderLocaleResolver();
		// SmartLocaleResolver localeResolver = new SmartLocaleResolver();
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("en"));
		// SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		// localeResolver.setDefaultLocale(StringUtils.parseLocaleString("en"));
		return localeResolver;
	}

	@Bean
    public Filter httpsEnforcerFilter(){
        return new HttpsEnforcer();
    }
}

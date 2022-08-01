package com.thalasoft.learnintouch.rest.controller;

import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.thalasoft.learnintouch.rest.config.ApplicationConfiguration;
import com.thalasoft.learnintouch.rest.config.WebConfiguration;
import com.thalasoft.learnintouch.rest.resource.AbstractResource;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class, WebConfiguration.class })
public abstract class AbstractControllerTest {

	@Autowired
    private WebApplicationContext webApplicationContext;

	@Autowired
    private FilterChainProxy springSecurityFilterChain;
	
	@Autowired
	protected ObjectMapper jacksonObjectMapper;

	protected MockHttpSession session;

    protected MockHttpServletRequest request;

	protected MockMvc mockMvc;
 
    protected static final String PASSWORD = "mypassword";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).addFilters(this.springSecurityFilterChain).build();
    }
    
	@Autowired
	private MessageSource messageSource;

	public String localizeErrorMessage(String errorCode, Object args[], Locale locale) {
		return messageSource.getMessage(errorCode, args, locale);
	}

	public String localizeErrorMessage(String errorCode, Locale locale) {
		return messageSource.getMessage(errorCode, null, locale);
	}

	public String localizeErrorMessage(String errorCode, Object args[]) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(errorCode, args, locale);
	}

	public String localizeErrorMessage(String errorCode) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(errorCode, null, locale);
	}

	protected <T extends Object> T deserialize(final MvcResult mvcResult, Class<T> clazz) throws Exception {
    	return jacksonObjectMapper.readValue(mvcResult.getResponse().getContentAsString(), clazz);
	}

	protected <T extends AbstractResource> T deserializeResource(final MvcResult mvcResult, Class<T> clazz) throws Exception {
    	return jacksonObjectMapper.readValue(mvcResult.getResponse().getContentAsString(), clazz);
	}

	protected <T extends AbstractResource> List<T> deserializeResources(final MvcResult mvcResult, Class<T> clazz) throws Exception {
		final CollectionType javaType = jacksonObjectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
		return jacksonObjectMapper.readValue(mvcResult.getResponse().getContentAsString(), javaType);
	}

}

package com.thalasoft.learnintouch.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;

import com.thalasoft.learnintouch.rest.utils.Common;
import com.thalasoft.learnintouch.rest.utils.UriMappingConstants;

public class GreetingControllerTest extends AbstractControllerTest {

	private HttpHeaders httpHeaders;
	
    @Before
    public void beforeAnyTest() throws Exception {
    	httpHeaders = Common.createAuthenticationHeaders("stephane" + ":" + PASSWORD);
    }
    
	@Test
    public void testSucceedsWithCorrectUserCredentials() throws Exception {
        this.mockMvc.perform(get(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.GREETING + UriMappingConstants.PATH_SEPARATOR + "hello").headers(httpHeaders)).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testFailsWithIncorrectUserCredentials() throws Exception {
        httpHeaders = Common.createAuthenticationHeaders("stephane" + ":" + "wrong_password");
        this.mockMvc.perform(get(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.GREETING + UriMappingConstants.PATH_SEPARATOR + "hello").headers(httpHeaders)).andExpect(status().isUnauthorized()).andReturn();
    }
    
	@Test
    public void testHello() throws Exception {
		String firstname = "Stephane";
        this.mockMvc.perform(get(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.GREETING + UriMappingConstants.PATH_SEPARATOR + "hello")
        .headers(httpHeaders)
        .param("message", firstname))        
        .andExpect(status().isOk())
        .andExpect(header().string("Location", Matchers.containsString("/hello")))
        .andExpect(jsonPath("$.message").value("Hello, " + firstname + "!"))
        .andReturn();
    }

}

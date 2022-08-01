package com.thalasoft.learnintouch.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thalasoft.learnintouch.rest.assembler.AdminResourceAssembler;
import com.thalasoft.learnintouch.rest.resource.AdminResource;
import com.thalasoft.learnintouch.rest.resource.Greeting;

@Controller
@ExposesResourceFor(AdminResource.class)
@RequestMapping("/error")
public class DummyController {

	@Autowired
	private AdminResourceAssembler adminResourceAssembler;
	
	@Autowired
    private MessageSource messageSource;
  
    @RequestMapping(value = "/npe", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ResponseBody
    public HttpEntity<Greeting> errorNPE() {
    	throw new NullPointerException();
    }
    
}

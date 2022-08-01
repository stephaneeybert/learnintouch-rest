package com.thalasoft.learnintouch.rest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.thalasoft.learnintouch.rest.resource.Greeting;
import com.thalasoft.learnintouch.rest.utils.UriMappingConstants;

@Controller
@RequestMapping(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.GREETING + UriMappingConstants.PATH_SEPARATOR)
public class GreetingController {

    private static final String TEMPLATE = "Hello, %s!";

	@RequestMapping(value = UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.HELLO, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpEntity<Greeting> hello(@RequestParam(value = "message", required = false, defaultValue = "World") String message, UriComponentsBuilder builder) {
        Greeting greeting = new Greeting(String.format(TEMPLATE, message));
        greeting.add(linkTo(methodOn(GreetingController.class).hello(message, builder)).withSelfRel());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(builder.path(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.GREETING + UriMappingConstants.PATH_SEPARATOR + "hello").buildAndExpand().toUri());
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");
        ResponseEntity<Greeting> responseEntity = new ResponseEntity<Greeting>(greeting, responseHeaders, HttpStatus.OK);        
        return responseEntity;
    }

}

package com.thalasoft.learnintouch.rest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.thalasoft.learnintouch.data.service.event.admin.EventAdmin;
import com.thalasoft.learnintouch.rest.utils.UriMappingConstants;

@Controller
@RequestMapping(UriMappingConstants.PATH_SEPARATOR)
public class RootController {

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpEntity<ResourceSupport> adminRoot(@PageableDefault Pageable pageable, PagedResourcesAssembler<EventAdmin> pagedResourcesAssembler, UriComponentsBuilder builder) {
        ResourceSupport resource = new ResourceSupport();
        resource.add(linkTo(methodOn(GreetingController.class).hello("Stephane", builder)).withRel("hello stephane"));
        resource.add(linkTo(methodOn(AdminController.class).search("searchTerm", pageable, pagedResourcesAssembler, builder)).withRel("search"));
        resource.add(linkTo(methodOn(AdminController.class).findById((long) 1, builder)).withRel("findById"));
        return new ResponseEntity<ResourceSupport>(resource, HttpStatus.OK);
	}
	
}

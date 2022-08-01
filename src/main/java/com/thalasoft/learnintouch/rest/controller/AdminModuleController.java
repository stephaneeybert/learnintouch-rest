package com.thalasoft.learnintouch.rest.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.thalasoft.learnintouch.data.jpa.domain.AdminModule;
import com.thalasoft.learnintouch.data.service.jpa.AdminModuleService;
import com.thalasoft.learnintouch.rest.assembler.AdminModuleResourceAssembler;
import com.thalasoft.learnintouch.rest.resource.AdminModuleResource;

@Controller
@ExposesResourceFor(AdminModuleResource.class)
@RequestMapping("/admin/{adminId}/module")
public class AdminModuleController {

    private static Logger logger = LoggerFactory.getLogger(AdminModuleController.class);

    @Autowired
	private AdminModuleService adminModuleService;

	@Autowired
	private AdminModuleResourceAssembler adminModuleResourceAssembler;
	
	@Autowired
    private MessageSource messageSource;
  
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ResponseBody
    public ResponseEntity<AdminModuleResource> findOne(@PathVariable Long id, UriComponentsBuilder builder) {
        HttpHeaders responseHeaders = new HttpHeaders();
        AdminModule adminModule = adminModuleService.findById(id);            
        responseHeaders.setLocation(builder.path("/admin/{id}").buildAndExpand(adminModule.getId()).toUri());
        AdminModuleResource adminModuleResource = adminModuleResourceAssembler.toResource(adminModule);
        ResponseEntity<AdminModuleResource> responseEntity = new ResponseEntity<AdminModuleResource>(adminModuleResource, responseHeaders, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AdminModuleResource> add(@Valid @RequestBody AdminModuleResource adminModuleResource, UriComponentsBuilder builder) {
        AdminModule adminModule = adminModuleService.add(adminModuleResource.toAdminModule());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(builder.path("/admin/module/{id}").buildAndExpand(adminModule.getId()).toUri());
        AdminModuleResource createdAdminModuleResource = adminModuleResourceAssembler.toResource(adminModule);
        ResponseEntity<AdminModuleResource> responseEntity = new ResponseEntity<AdminModuleResource>(createdAdminModuleResource, responseHeaders, HttpStatus.CREATED);
        return responseEntity;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AdminModuleResource> update(@PathVariable Long id, @RequestBody AdminModuleResource adminModuleResource, UriComponentsBuilder builder) {
        HttpHeaders responseHeaders = new HttpHeaders();
        AdminModule updatedAdminModule;
        updatedAdminModule = adminModuleService.update(adminModuleResource.toAdminModule(id));
        responseHeaders.setLocation(builder.path("/admin/module/{id}").buildAndExpand(updatedAdminModule.getId()).toUri());
        AdminModuleResource updatedAdminModuleResource = adminModuleResourceAssembler.toResource(updatedAdminModule);
        return new ResponseEntity<AdminModuleResource>(updatedAdminModuleResource, responseHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AdminModuleResource> delete(@PathVariable Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
       	adminModuleService.deleteById(id);            
        return new ResponseEntity<AdminModuleResource>(responseHeaders, HttpStatus.NO_CONTENT);
    }
    
}

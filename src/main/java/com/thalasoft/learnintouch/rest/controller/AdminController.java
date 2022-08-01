package com.thalasoft.learnintouch.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.thalasoft.learnintouch.data.service.event.admin.CreateAdminEvent;
import com.thalasoft.learnintouch.data.service.event.admin.CreatedAdminEvent;
import com.thalasoft.learnintouch.data.service.event.admin.DeleteAdminEvent;
import com.thalasoft.learnintouch.data.service.event.admin.EventAdmin;
import com.thalasoft.learnintouch.data.service.event.admin.GetOneAdminEvent;
import com.thalasoft.learnintouch.data.service.event.admin.GotOneAdminEvent;
import com.thalasoft.learnintouch.data.service.event.admin.SearchAdminsEvent;
import com.thalasoft.learnintouch.data.service.event.admin.SearchedAdminsEvent;
import com.thalasoft.learnintouch.data.service.event.admin.UpdateAdminEvent;
import com.thalasoft.learnintouch.data.service.event.admin.UpdatedAdminEvent;
import com.thalasoft.learnintouch.data.service.jpa.AdminService;
import com.thalasoft.learnintouch.rest.assembler.AdminResourceAssembler;
import com.thalasoft.learnintouch.rest.resource.AdminResource;
import com.thalasoft.learnintouch.rest.utils.PageResource;
import com.thalasoft.learnintouch.rest.utils.UriMappingConstants;

@Controller
@ExposesResourceFor(AdminResource.class)
@RequestMapping(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS)
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired 
	private AdminService adminService;

	@Autowired
	private AdminResourceAssembler adminResourceAssembler;
	
	@Autowired
    private MessageSource messageSource;

    @RequestMapping(value = UriMappingConstants.PATH_SEPARATOR + "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AdminResource> findById(@PathVariable Long id, UriComponentsBuilder builder) {
        HttpHeaders responseHeaders = new HttpHeaders();
        GotOneAdminEvent oneAdminEvent = adminService.findById(new GetOneAdminEvent(id));
        if (oneAdminEvent == null) {
          return new ResponseEntity<AdminResource>(responseHeaders, HttpStatus.NOT_FOUND);
        } else {
          AdminResource adminResource = adminResourceAssembler.toResource(oneAdminEvent.getEventAdmin());
          responseHeaders.setLocation(builder.path(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS + UriMappingConstants.PATH_SEPARATOR + "{id}").buildAndExpand(oneAdminEvent.getEventAdmin().getId()).toUri());
          ResponseEntity<AdminResource> responseEntity = new ResponseEntity<AdminResource>(adminResource, responseHeaders, HttpStatus.OK);
          return responseEntity;
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AdminResource> add(@Valid @RequestBody AdminResource adminResource, UriComponentsBuilder builder) {
        CreatedAdminEvent adminCreatedEvent = adminService.add(new CreateAdminEvent(adminResource.toEventAdmin()));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(builder.path(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS + UriMappingConstants.PATH_SEPARATOR + "{id}").buildAndExpand(adminCreatedEvent.getAdminId()).toUri());
        AdminResource createdAdminResource = adminResourceAssembler.toResource(adminCreatedEvent.getEventAdmin());
        ResponseEntity<AdminResource> responseEntity = new ResponseEntity<AdminResource>(createdAdminResource, responseHeaders, HttpStatus.CREATED);
        return responseEntity;
    }
    
    @RequestMapping(value = UriMappingConstants.PATH_SEPARATOR + "{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AdminResource> update(@PathVariable Long id, @RequestBody AdminResource adminResource, UriComponentsBuilder builder) {
        HttpHeaders responseHeaders = new HttpHeaders();
        UpdatedAdminEvent updatedAdminEvent;
        updatedAdminEvent = adminService.update(new UpdateAdminEvent(adminResource.toEventAdmin(id)));
        responseHeaders.setLocation(builder.path(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS + UriMappingConstants.PATH_SEPARATOR + "{id}").buildAndExpand(updatedAdminEvent.getEventAdmin().getId()).toUri());
        AdminResource updatedAdminResource = adminResourceAssembler.toResource(updatedAdminEvent.getEventAdmin());
        return new ResponseEntity<AdminResource>(updatedAdminResource, responseHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = UriMappingConstants.PATH_SEPARATOR + "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AdminResource> delete(@PathVariable Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        adminService.deleteById(new DeleteAdminEvent(id));            
        return new ResponseEntity<AdminResource>(responseHeaders, HttpStatus.NO_CONTENT);
    }
    
	@RequestMapping(value = UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.SEARCH + "OLD", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PageResource<AdminResource>> search_OLD_NOT_USED(@RequestParam(value = "searchTerm") String searchTerm, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size, UriComponentsBuilder builder) {
        HttpHeaders responseHeaders = new HttpHeaders();
		Pageable pageRequest = buildPageRequest(page, size);
        SearchedAdminsEvent searchedAdminsEvent;
       	searchedAdminsEvent = adminService.search(new SearchAdminsEvent(searchTerm, pageRequest));            

        responseHeaders.setLocation(builder.path(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS + UriMappingConstants.PATH_SEPARATOR + "search").queryParam("searchTerm", searchTerm).queryParam("page", page).queryParam("size", size).buildAndExpand(searchedAdminsEvent.getSearchTerm()).toUri());
   		List<AdminResource> adminResources = adminResourceAssembler.toResources(searchedAdminsEvent.getEventAdmins());
   		Page<AdminResource> adminResourcePages = new PageImpl<AdminResource>(adminResources, pageRequest, searchedAdminsEvent.getTotalElements());
   		PageResource<AdminResource> adminPageResources = new PageResource<AdminResource>(adminResourcePages, "page", "size");
        return new ResponseEntity<PageResource<AdminResource>>(adminPageResources, responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.SEARCH, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PagedResources<AdminResource>> search(@RequestParam(value = "searchTerm") String searchTerm, @PageableDefault Pageable pageable, PagedResourcesAssembler<EventAdmin> pagedResourcesAssembler, UriComponentsBuilder builder) {
        HttpHeaders responseHeaders = new HttpHeaders();
		Pageable pageRequest = buildPageRequest(pageable.getPageNumber(), pageable.getPageSize());
        SearchedAdminsEvent searchedAdminsEvent;
       	searchedAdminsEvent = adminService.search(new SearchAdminsEvent(searchTerm, pageRequest));            

        responseHeaders.setLocation(builder.path(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS + UriMappingConstants.PATH_SEPARATOR + "search").queryParam("searchTerm", searchTerm).queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize()).queryParam("sort", pageable.getSort()).buildAndExpand(searchedAdminsEvent.getSearchTerm()).toUri());
   		Page<EventAdmin> eventAdminPages = new PageImpl<EventAdmin>(searchedAdminsEvent.getEventAdmins(), pageable, searchedAdminsEvent.getTotalElements());
   		PagedResources<AdminResource> adminPagedResources = pagedResourcesAssembler.toResource(eventAdminPages, adminResourceAssembler);
        return new ResponseEntity<PagedResources<AdminResource>>(adminPagedResources, responseHeaders, HttpStatus.OK);
	}

	private Pageable buildPageRequest(int pageIndex, int pageSize) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "lastname"), new Sort.Order(Sort.Direction.ASC, "firstname"));
		return new PageRequest(pageIndex, pageSize, sort);
	}

}

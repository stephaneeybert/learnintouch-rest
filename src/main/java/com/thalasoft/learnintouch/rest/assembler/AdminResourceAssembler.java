package com.thalasoft.learnintouch.rest.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.thalasoft.learnintouch.data.service.event.admin.EventAdmin;
import com.thalasoft.learnintouch.rest.controller.AdminController;
import com.thalasoft.learnintouch.rest.resource.AdminResource;

@Component
public class AdminResourceAssembler extends ResourceAssemblerSupport<EventAdmin, AdminResource> {

    public AdminResourceAssembler() {
        super(AdminController.class, AdminResource.class);
    }

    public AdminResource toResource(EventAdmin eventAdmin) {
        AdminResource adminResource = createResourceWithId(eventAdmin.getId(), eventAdmin);
        AdminResource.fromEventAdmin(adminResource, eventAdmin);
        adminResource.add(linkTo(AdminController.class).slash(eventAdmin.getId()).slash("module").withRel("module"));        
        return adminResource;
    }

    @Override
    public List<AdminResource> toResources(Iterable<? extends EventAdmin> eventAdmins) {
        List<AdminResource> adminResources = new ArrayList<AdminResource>();
        for(EventAdmin eventAdmin : eventAdmins) {
        	AdminResource adminResource = createResourceWithId(eventAdmin.getId(), eventAdmin);
            AdminResource.fromEventAdmin(adminResource, eventAdmin);
            adminResource.add(linkTo(AdminController.class).slash(eventAdmin.getId()).slash("module").withRel("module"));            
            adminResources.add(adminResource);
        }
        return adminResources;
    }
    
//    public Page<AdminResource> toResource(List<EventAdmin> eventAdmins) {
//    	Page<AdminResource> adminResources = new Page
//    			for (EventAdmin eventAdmin : eventAdmins) {
//    				AdminResource adminResource = createResourceWithId(eventAdmin.getId(), eventAdmin);
//    				AdminResource.fromEventAdmin(adminResource, eventAdmin);
//    				adminResource.add(linkTo(AdminController.class).slash(eventAdmin.getId()).slash("module").withRel("module"));
//    				
//    			}
//    	
//    	
//    	
//    	return adminResource;
//    }
    
//    @Override
//    protected AdminResource instantiateResource(EventAdmin eventAdmin) {
//        return new AdminResource(eventAdmin.getId(), eventAdmin.getFirstname());
//    }
    
}

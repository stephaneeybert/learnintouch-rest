package com.thalasoft.learnintouch.rest.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.thalasoft.learnintouch.data.jpa.domain.AdminModule;
import com.thalasoft.learnintouch.data.service.event.admin.EventAdmin;
import com.thalasoft.learnintouch.rest.controller.AdminController;
import com.thalasoft.learnintouch.rest.controller.AdminModuleController;
import com.thalasoft.learnintouch.rest.resource.AdminModuleResource;
import com.thalasoft.learnintouch.rest.resource.AdminResource;

@Component
public class AdminModuleResourceAssembler extends ResourceAssemblerSupport<AdminModule, AdminModuleResource> {

//	@Autowired TODO http://stackoverflow.com/questions/25602402/how-to-add-hateoas-links-in-a-sub-resource
//	private AdminResourceAssembler adminResourceAssembler;

    public AdminModuleResourceAssembler() {
        super(AdminModuleController.class, AdminModuleResource.class);
    }

    public AdminModuleResource toResource(AdminModule adminModule) {
        AdminModuleResource adminModuleResource = createResourceWithId(adminModule.getId(), adminModule);
//        BeanUtils.copyProperties(adminModuleResource, adminModuleResource);
        adminModuleResource.add(linkTo(AdminModuleController.class).slash(adminModuleResource.getId()).withSelfRel());

//        EventAdmin eventAdmin = new EventAdmin();
//        BeanUtils.copyProperties(adminModule.getAdmin(), eventAdmin);
//        AdminResource adminResource = adminResourceAssembler.createResourceWithId(adminModule.getAdmin().getId(), eventAdmin);
//        BeanUtils.copyProperties(eventAdmin, adminResource);
//        adminResource.add(linkTo(AdminController.class).slash(eventAdmin.getId()).slash("module").withRel("module"));        

        return adminModuleResource;
    }

}

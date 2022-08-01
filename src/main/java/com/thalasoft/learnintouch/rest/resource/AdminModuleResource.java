package com.thalasoft.learnintouch.rest.resource;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.ResourceSupport;

import com.thalasoft.learnintouch.data.jpa.domain.Admin;
import com.thalasoft.learnintouch.data.jpa.domain.AdminModule;

public class AdminModuleResource extends ResourceSupport {

    @NotEmpty
    private String module;
    @NotEmpty
    private Admin admin;

    public AdminModuleResource() {
    }

    public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public AdminModule toAdminModule(Long id) {
		AdminModule adminModule = this.toAdminModule();
		
		adminModule.setId(id);
		
		return adminModule;
	}
	
    public AdminModule toAdminModule() {
    	AdminModule adminModule = new AdminModule();

        BeanUtils.copyProperties(this, adminModule);
        
        return adminModule;
    }

}

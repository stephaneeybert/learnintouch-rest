package com.thalasoft.learnintouch.rest.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.thalasoft.learnintouch.data.jpa.domain.Admin;
import com.thalasoft.learnintouch.data.service.event.admin.CreateAdminEvent;
import com.thalasoft.learnintouch.data.service.event.admin.CreatedAdminEvent;
import com.thalasoft.learnintouch.data.service.jpa.AdminService;
import com.thalasoft.learnintouch.rest.resource.AdminResource;

@Component
@Profile("bootstrap")
public class WebBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory.getLogger(WebBootstrap.class);

	@Autowired
	private AdminService adminService;

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		addDataFixture();
	}
	
	private void addDataFixture() {
        Admin admin0 = new Admin.AdminBuilder("Joe", "TheBouncer", "joethebouncer@thalasoft.com", "joethebouncer")
        .setPassword("e41de4c55873f9c000f4cdaac6efd3aa")
        .setPasswordSalt("7bc7bf5f94fef7c7106afe5c3a40a2")
        .build();

        Admin loadedAdmin = adminService.findByEmail(admin0.getEmail());
        if (loadedAdmin == null) {
	        admin0 = adminService.add(admin0);
	        loadedAdmin = adminService.findById(admin0.getId());
	        if (loadedAdmin != null) {
	        	logger.debug("==============>>> Joe The Bouncer was saved and loaded.");
	        }

	    	List<AdminResource> manyAdminResources;
			manyAdminResources = new ArrayList<AdminResource>();
			for (int i = 0; i < 50; i++) {
				String index = intToString(i, 2);
				AdminResource oneAdminResource = new AdminResource();
				oneAdminResource.setFirstname("zfirstname" + UUID.randomUUID().toString() + index);
				oneAdminResource.setLastname("zlastname" + index);
				oneAdminResource.setEmail("zemail@nokia.com" + index);
				oneAdminResource.setLogin("zlogin" + index);
				oneAdminResource.setPassword("zpassword" + index);
				oneAdminResource.setPasswordSalt("");
				manyAdminResources.add(oneAdminResource);
				
				CreatedAdminEvent oneAdminCreatedEvent = adminService.add(new CreateAdminEvent(oneAdminResource.toEventAdmin()));
				manyAdminResources.add(AdminResource.fromEventAdmin(oneAdminCreatedEvent.getEventAdmin()));
			}
        } else {
        	logger.debug("==============>>> The data already exists.");
        }
	}
 
	private String intToString(int num, int digits) {
		String output = Integer.toString(num);
		while (output.length() < digits) {
			output = "0" + output;
		}
		return output;
	}
 
}

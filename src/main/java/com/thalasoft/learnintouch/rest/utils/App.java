package com.thalasoft.learnintouch.rest.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.thalasoft.learnintouch.data.jpa.domain.Admin;
import com.thalasoft.learnintouch.data.service.jpa.AdminService;
import com.thalasoft.learnintouch.rest.config.ApplicationConfiguration;
import com.thalasoft.learnintouch.rest.config.WebConfiguration;
import com.thalasoft.learnintouch.rest.config.WebSecurityConfiguration;

@ContextConfiguration(classes = { ApplicationConfiguration.class, WebSecurityConfiguration.class, WebConfiguration.class })
public class App {

	private static Logger logger = LoggerFactory.getLogger(App.class);

	@Autowired
    private static WebApplicationContext webApplicationContext;
	
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String password = "";
        System.out.print("Give your password: ");
        try {
            password = in.readLine();
        } catch (Exception e) {
            System.out.println("Caught an exception!");
        }
        System.out.println("Password: " + password);

        Admin admin0 = new Admin.AdminBuilder("Joe", "TheBouncer", "joethebouncer@thalasoft.com", "joethebouncer")
        .setPassword("e41de4c55873f9c000f4cdaac6efd3aa")
        .setPasswordSalt("7bc7bf5f94fef7c7106afe5c3a40a2")
        .build();
        
        AdminService adminService = (AdminService) webApplicationContext.getBean("adminService");

        Admin loadedAdmin = adminService.findByEmail(admin0.getEmail());
        if (loadedAdmin == null) {
	        admin0 = adminService.add(admin0);
	        loadedAdmin = adminService.findById(admin0.getId());
	        if (loadedAdmin != null) {
	        	logger.debug("==============>>> Joe The Bouncer was saved and loaded.");
	        }
        } else {
        	logger.debug("==============>>> Joe The Bouncer already exists.");
        }
    }

}

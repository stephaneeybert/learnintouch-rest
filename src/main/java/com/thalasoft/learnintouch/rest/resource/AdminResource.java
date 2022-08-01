package com.thalasoft.learnintouch.rest.resource;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.thalasoft.learnintouch.data.service.event.admin.EventAdmin;

public class AdminResource extends AbstractResource {

    private String firstname;
    private String lastname;
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @Length(min=8, max=20)
    private String login;
    private String password;
    private String passwordSalt;

    public AdminResource() {
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }
    
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
    
    public EventAdmin toEventAdmin(Long id) {
    	EventAdmin eventAdmin = this.toEventAdmin();
    	
    	eventAdmin.setId(id);
    	
    	return eventAdmin;
    }
    
    public EventAdmin toEventAdmin() {
        EventAdmin eventAdmin = new EventAdmin();

        return toEventAdmin(eventAdmin);
    }

    public EventAdmin toEventAdmin(EventAdmin eventAdmin) {
        eventAdmin.setEmail(this.getEmail());
        eventAdmin.setFirstname(this.getFirstname());
        eventAdmin.setLastname(this.getLastname());
        eventAdmin.setLogin(this.getLogin());
        eventAdmin.setPassword(this.getPassword());
        eventAdmin.setPasswordSalt(this.getPasswordSalt());
        
        return eventAdmin;
    }

    public static AdminResource fromEventAdmin(EventAdmin eventAdmin) {
        AdminResource adminResource = new AdminResource();
        return fromEventAdmin(adminResource, eventAdmin);
    }

    public static AdminResource fromEventAdmin(AdminResource adminResource, EventAdmin eventAdmin) {
    	adminResource.setEmail(eventAdmin.getEmail());
    	adminResource.setFirstname(eventAdmin.getFirstname());
    	adminResource.setLastname(eventAdmin.getLastname());
    	adminResource.setLogin(eventAdmin.getLogin());
    	adminResource.setPassword(eventAdmin.getPassword());
    	adminResource.setPasswordSalt(eventAdmin.getPasswordSalt());
    	adminResource.setResourceId(eventAdmin.getId());
        return adminResource;
    }

}

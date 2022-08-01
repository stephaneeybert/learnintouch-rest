package com.thalasoft.learnintouch.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.thalasoft.learnintouch.data.jpa.domain.Admin;
import com.thalasoft.learnintouch.data.service.jpa.AdminService;
import com.thalasoft.learnintouch.rest.resource.AdminResource;
import com.thalasoft.learnintouch.rest.utils.Common;
import com.thalasoft.learnintouch.rest.utils.UriMappingConstants;

public class AdminExceptionTest extends AbstractControllerTest {

    private static Logger logger = LoggerFactory.getLogger(AdminExceptionTest.class);

    @Autowired
    AdminService adminService;

    private Admin admin0;
    private HttpHeaders httpHeaders;
    
    @Before
    public void beforeAnyTest() throws Exception {
        httpHeaders = Common.createAuthenticationHeaders("stephane" + ":" + PASSWORD);

        admin0 = new Admin.AdminBuilder("Stephane", "Eybert", "mittiprovence@yahoo.se", "stephane")
        .setPassword("e41de4c55873f9c000f4cdaac6efd3aa")
        .setPasswordSalt("7bc7bf5f94fef7c7106afe5c3a40a2")
        .build();
    }

    @After
    public void afterAnyTest() throws Exception {
    	if (null != admin0.getId()) {
    		adminService.delete(admin0.getId());
    	}
    }

    @Test
    public void testInvalidAdminIdTriggersTypeMismatchException() throws Exception {
        String id = "dummy";
        
        MvcResult resultGet = this.mockMvc.perform(
                get(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS + UriMappingConstants.PATH_SEPARATOR + id)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.url").value("http://localhost/admins/dummy"))
                .andExpect(jsonPath("$.message").value("The entity id \"dummy\" had a type mismatch."))
                .andReturn();
    }
    
    @Test
    public void testAddingExistingAdminTriggersAdminAlreadyExistException() throws Exception {
        MvcResult resultPost = this.mockMvc.perform(
                post(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS).headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"firstname\" : \"" + admin0.getFirstname() + "\", \"lastname\" : \"" + admin0.getLastname() + "\", \"email\" : \"" + admin0.getEmail() + "\", \"login\" : \"" + admin0.getLogin() + "\", \"password\" : \"" + admin0.getPassword() + "\", \"passwordSalt\" : \"" + admin0.getPasswordSalt() + "\" }")
            ).andDo(print())
            .andExpect(status().isCreated())
            .andReturn();
        
        AdminResource retrievedAdminResource = deserializeResource(resultPost, AdminResource.class);
        admin0.setId(retrievedAdminResource.getResourceId());
        
        resultPost = this.mockMvc.perform(
                post(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS).headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"firstname\" : \"" + admin0.getFirstname() + "\", \"lastname\" : \"" + admin0.getLastname() + "\", \"email\" : \"" + admin0.getEmail() + "\", \"login\" : \"" + admin0.getLogin() + "\", \"password\" : \"" + admin0.getPassword() + "\", \"passwordSalt\" : \"" + admin0.getPasswordSalt() + "\" }")
            ).andDo(print())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value("The admin with email \"mittiprovence@yahoo.se\" already exists."))
            .andReturn();
    }

    @Test
    public void testNullPointerException() throws Exception {
        MvcResult resultPost = this.mockMvc.perform(
                get("/error/npe").headers(httpHeaders)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.message").value("There was an NPE error."))
            .andReturn();
    }
    
    @Test
    public void testExceptionLocalizedMessage() throws Exception {
        MvcResult resultGet = this.mockMvc.perform(
                get("/error/npe").headers(httpHeaders)
                .param("lang", "fr")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.message").value("Une erreur inconnue s'est produite. Veuillez nous excuser."))
            .andReturn();

        resultGet = this.mockMvc.perform(
                get("/error/npe?lang=fr").headers(httpHeaders)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.message").value("Une erreur inconnue s'est produite. Veuillez nous excuser."))
            .andReturn();

//        httpHeaders.add("Accept-Language", "fr");
//        resultGet = this.mockMvc.perform(
//                get("/error/npe").headers(httpHeaders)
//                .accept(MediaType.APPLICATION_JSON)
//            )
//            .andExpect(status().isInternalServerError())
//            .andExpect(jsonPath("$.message").value("Une erreur inconnue s'est produite. Veuillez nous excuser."))
//            .andReturn();
    }
    
}

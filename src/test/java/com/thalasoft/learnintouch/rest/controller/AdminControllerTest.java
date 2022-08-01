package com.thalasoft.learnintouch.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.*;



import com.thalasoft.learnintouch.data.service.event.admin.CreateAdminEvent;
import com.thalasoft.learnintouch.data.service.event.admin.CreatedAdminEvent;
import com.thalasoft.learnintouch.data.service.jpa.AdminService;
import com.thalasoft.learnintouch.rest.resource.AdminResource;
import com.thalasoft.learnintouch.rest.utils.Common;
import com.thalasoft.learnintouch.rest.utils.UriMappingConstants;
//import static com.thalasoft.learnintouch.data.jpa.assertion.AdminAssert.assertThatAdmin;
//import static com.thalasoft.learnintouch.rest.assertion.AdminResourceAssert.assertThatAdminResource;

public class AdminControllerTest extends AbstractControllerTest {

    private static Logger logger = LoggerFactory.getLogger(AdminControllerTest.class);

    @Autowired
    AdminService adminService;
    
    private AdminResource adminResource0;
	private List<AdminResource> manyAdminResources;
	private HttpHeaders httpHeaders;
	
    @Before
    public void beforeAnyTest() throws Exception {
		httpHeaders = Common.createAuthenticationHeaders("stephane" + ":" + PASSWORD);
		
        adminResource0 = new AdminResource();
        adminResource0.setFirstname("Stephane");
        adminResource0.setLastname("Eybert");
        adminResource0.setEmail("mittiprovence@yahoo.se");
        adminResource0.setLogin("stephane");
        adminResource0.setPassword("e41de4c55873f9c000f4cdaac6efd3aa");
        adminResource0.setPasswordSalt("7bc7bf5f94fef7c7106afe5c3a40a2");
		
		manyAdminResources = new ArrayList<AdminResource>();
		for (int i = 0; i < 99; i++) {
			String index = intToString(i, 2);
			AdminResource oneAdminResource = new AdminResource();
			oneAdminResource.setFirstname("zfirstname" + index);
			oneAdminResource.setLastname("zlastname" + index);
			oneAdminResource.setEmail("zemail" + index + "@thalasoft.com");
			oneAdminResource.setLogin("zlogin" + index);
			oneAdminResource.setPassword("zpassword" + index);
			oneAdminResource.setPasswordSalt("");
			
			CreatedAdminEvent oneAdminCreatedEvent = adminService.add(new CreateAdminEvent(oneAdminResource.toEventAdmin()));
			AdminResource adminResource = AdminResource.fromEventAdmin(oneAdminCreatedEvent.getEventAdmin());
			manyAdminResources.add(adminResource);
		}
    }
    
    @After
    public void afterAnyTest() throws Exception {
    	if (null != adminResource0.getResourceId()) {
    		adminService.delete(adminResource0.getResourceId());
    	}
		for (AdminResource adminResource : manyAdminResources) {
	    	if (null != adminResource.getResourceId()) {
	    		adminService.delete(adminResource.getResourceId());
	    	}
		}
    }

	private String intToString(int num, int digits) {
		String output = Integer.toString(num);
		while (output.length() < digits) {
			output = "0" + output;
		}
		return output;
	}
   
	@Test
	public void testPostInvalidAdminShouldReturnValidationErrorMessages() throws Exception {
		MvcResult resultPost = this.mockMvc.perform(
		          post(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS).headers(httpHeaders)
		          .contentType(MediaType.APPLICATION_JSON)
		          .accept(MediaType.APPLICATION_JSON)
		          .content("{ \"firstname\" : \"" + adminResource0.getFirstname() + "\", \"lastname\" : \"" + adminResource0.getLastname() + "\", \"email\" : \"\", \"login\" : \"short\", \"password\" : \"" + adminResource0.getPassword() + "\", \"passwordSalt\" : \"" + adminResource0.getPasswordSalt() + "\" }")
		)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").value("The entity form arguments were invalid."))
		.andExpect(jsonPath("$.fieldErrors[?(@.fieldName=='login')].fieldError").value("The admin login is not long enough."))
		.andExpect(jsonPath("$.fieldErrors[?(@.fieldName=='email')].fieldError").value("The admin email is empty."))
		.andReturn();
	}
 
    @Test
    public void testCrudOperations() throws Exception {
        MvcResult resultPost = this.mockMvc.perform(
            post(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS).headers(httpHeaders)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{ \"firstname\" : \"" + adminResource0.getFirstname() + "\", \"lastname\" : \"" + adminResource0.getLastname() + "\", \"email\" : \"" + adminResource0.getEmail() + "\", \"login\" : \"" + adminResource0.getLogin() + "\", \"password\" : \"" + adminResource0.getPassword() + "\", \"passwordSalt\" : \"" + adminResource0.getPasswordSalt() + "\" }")
        ).andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.firstname").exists())
        .andExpect(jsonPath("$.firstname").value(adminResource0.getFirstname()))
        .andExpect(jsonPath("$.lastname").value(adminResource0.getLastname()))
        .andExpect(jsonPath("$.email").value(adminResource0.getEmail()))
        .andExpect(jsonPath("$.login").value(adminResource0.getLogin()))
        .andExpect(jsonPath("$.password").value(adminResource0.getPassword()))
        .andExpect(jsonPath("$.passwordSalt").value(adminResource0.getPasswordSalt()))
        .andExpect(header().string("Location", Matchers.containsString("/admins/")))
        .andReturn();

// TODO How to use jsonPath, or rather REST, with AssertJ ?    
//		assertThatAdminResource(loadedAdmin)
//		.hasAnIdNotNull()
//		.hasEmail(admin.getEmail())
//		.hasPassword(admin.getPassword())
//		.isSameAs(admin);
		
        String location = resultPost.getResponse().getHeader("Location");
        Pattern pattern = Pattern.compile("(\\d+)$");
        Matcher matcher = pattern.matcher(location);
        matcher.find();
        Long id = Long.parseLong(matcher.group(), 10);

        MvcResult resultGet = this.mockMvc.perform(
                get(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS + UriMappingConstants.PATH_SEPARATOR + id)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String changedFirstname = "Cyril";
        MvcResult resultPut = this.mockMvc.perform(
                put(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS + UriMappingConstants.PATH_SEPARATOR + id)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{ \"firstname\" : \"" + changedFirstname + "\", \"lastname\" : \"" + adminResource0.getLastname() + "\", \"email\" : \"" + adminResource0.getEmail() + "\", \"login\" : \"" + adminResource0.getLogin() + "\", \"password\" : \"" + adminResource0.getPassword() + "\", \"passwordSalt\" : \"" + adminResource0.getPasswordSalt() + "\" }")
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstname").value(changedFirstname))
            .andReturn();
        
        String responseContent = resultPut.getResponse().getContentAsString();
        logger.debug(">>>>>>>>>>>>>>>>>>>> responseContent: " + responseContent);

        this.mockMvc.perform(
                delete(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS + UriMappingConstants.PATH_SEPARATOR + id)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        logger.debug(">>>>>>>>>>>>>>>>>>>> id: " + id);
        this.mockMvc.perform(
                get(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS + UriMappingConstants.PATH_SEPARATOR + id)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void testSearchShouldReturnSome() throws Exception {
        MvcResult resultGet = this.mockMvc.perform(
            get(UriMappingConstants.PATH_SEPARATOR + UriMappingConstants.ADMINS + UriMappingConstants.PATH_SEPARATOR + "search").headers(httpHeaders)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .param("searchTerm", "irstnam")
            .param("page", "0")
            .param("size", "10")
        ).andDo(print())
        .andExpect(status().isOk())        
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.page.size").value(10))
        .andExpect(jsonPath("$.page.totalElements").value(99))
        .andExpect(jsonPath("$.page.totalPages").value(10))
        .andExpect(jsonPath("$.page.number").value(0))
		.andExpect(jsonPath("$.content[0].firstname").exists())
		.andExpect(jsonPath("$.content[0].firstname").value(manyAdminResources.get(0).getFirstname()))
        .andExpect(jsonPath("$.content[0].lastname").value(manyAdminResources.get(0).getLastname()))
        .andExpect(jsonPath("$.content[0].email").value(manyAdminResources.get(0).getEmail()))
        .andExpect(jsonPath("$.content[0].login").value(manyAdminResources.get(0).getLogin()))
        .andExpect(jsonPath("$.content[0].password").value(manyAdminResources.get(0).getPassword()))
        .andExpect(jsonPath("$.content[0].passwordSalt").value(manyAdminResources.get(0).getPasswordSalt()))
        .andExpect(jsonPath("$.content[0].links[0].rel").value("self"))
        .andExpect(jsonPath("$.content[0].links[1].rel").value("module"))
        .andExpect(header().string("Location", Matchers.containsString("/admins/search?searchTerm=")))
        .andReturn();

        String responseContent = resultGet.getResponse().getContentAsString();
    }
    
    
//  @Test
//  public void testDummy() throws Exception {
//      HttpHeaders headers = new HttpHeaders();
//      HttpEntity<String> entity = new HttpEntity<String>(helloWorld, headers);
//      URI location = template.postForLocation("http://example.com", entity);
      
//      RestTemplate restTemplate = new RestTemplate();
//      ResponseEntity<Order> responseEntity = restTemplate.postForEntity("http://localhost:8080/aggregators/orders", requestEntity, Order.class);
//      String path = responseEntity.getHeaders().getLocation().getPath();
//      assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//      assertTrue(path.startsWith("/aggregators/orders/"));
//      Order order = responseEntity.getBody();
//      System.out.println ("The Order ID is " + order.getKey());
//      System.out.println ("The Location is " + responseEntity.getHeaders().getLocation());
//      assertEquals(2, order.getItems().size());

//      Map<String, String> vars = new HashMap<String, String>();
//      vars.put("hotel", "42");
//      vars.put("booking", "21");
//      String result = restTemplate.getForObject("http://example.com/hotels/{hotel}/bookings/{booking}", String.class, vars);

//      Greeting greeting = restTemplate.getForObject("/hotels/{id}", Greeting.class, 42);
//      mockServer.verify();

//      String jsonStringUser = "{" + "\"id\":\"\"" + "," + "\"firstName\":\"nicolas\"" + "," + "\"lastName\":\"loriente\"" + "}";
//      HttpHeaders headers = new HttpHeaders();
//      headers.setContentType(MediaType.APPLICATION_JSON);
//      HttpEntity request = new HttpEntity(jsonStringUser, headers);
              
//      User user = new User();
//      user.setFirstName("nicolas");
//      user.setLastName("loriente");
//      User returnedUser = restTemplate.postForObject("http://localhost:8080/Rest/user/save", user, User.class);
//      assertNotNull(returnedUser.getId());
//      assertEquals("nicolas", returnedUser.getFirstName());
//      assertEquals("loriente", returnedUser.getLastName());
      
//      String path = responseEntity.getHeaders().getLocation().getPath();
//      assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//      assertTrue(path.startsWith("/admin/greeting/"));
//      logger.debug("The greeting id: " + greeting.getId());
//      logger.debug("The greeting message: " + greeting.getMessage());
//      assertEquals(2, greeting.getLinks().size());
//      for (Link link : greeting.getLinks()) {
//          logger.debug("The link href: " + link.getHref());
//      }
//      logger.debug("The Location is " + responseEntity.getHeaders().getLocation());

//    UserDomain user = new UserDomain();
//    user.setFirstName("johnny");
//    session.setAttribute("sessionParm",user);      
//  }

//			jsonPath("$.links").value(collectionWithSize(equalTo(2)))
//			jsonPath("$.links[?(@.rel==doors)].href[0]").value(GAME_LOCATION+"doors")        
//        Cookie c = result.getResponse().getCookie("my-cookie");
//        assertThat(c.getValue().length(), greaterThan(10));
//        assertThat(link.getRel(), is("people"));
//        assertThat(link.getHref(), endsWith("/people"));    
//        MockHttpServletResponse response = result.getResponse();
//        String location = response.getHeader("Location");
//        Pattern pattern = Pattern.compile("\\Aview/[0-9]+\\z");
//        assertTrue(pattern.matcher(location).find());
 
//        .andExpect(.redirectedUrlPattern("view/[0-9]+"))
//    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
//        return new ResultMatcher() {
//            public void match(MvcResult result) {
//                Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
//                assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
//            }
//        };
//    }
    
}

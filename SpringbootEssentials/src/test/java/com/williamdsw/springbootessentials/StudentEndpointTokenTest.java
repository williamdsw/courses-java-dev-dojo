package com.williamdsw.springbootessentials;

import com.williamdsw.springbootessentials.model.Student;
import com.williamdsw.springbootessentials.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author William
 */

@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class StudentEndpointTokenTest
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    @Autowired 
    private TestRestTemplate testRestTemplate;
    
    @LocalServerPort
    private int port;
    
    @MockBean
    private StudentRepository studentRepository;
    
    @Autowired
    private MockMvc mockMvc;
    
    private HttpEntity<Void> protectedHeader;
    private HttpEntity<Void> adminHeader;
    private HttpEntity<Void> wrongHeader;
    
    //------------------------------------------------------------------------//
    // HELPER FUNCTIONS
    
    @BeforeEach
    public void configProtectedHeaders ()
    {
    	String userJson = "{ \"username\": \"james\", \"password\": \"metallica\" }";
    	HttpHeaders headers = testRestTemplate.postForEntity ("/login", userJson, String.class).getHeaders ();
    	this.protectedHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void configAdminHeaders ()
    {
    	String adminJson = "{ \"username\": \"dave\", \"password\": \"devdojo\" }";
    	HttpHeaders headers = testRestTemplate.postForEntity ("/login", adminJson, String.class).getHeaders ();
    	this.adminHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void configWrongHeaders ()
    {
    	HttpHeaders headers = new HttpHeaders ();
    	headers.add ("Authorization", "11111");
    	this.wrongHeader = new HttpEntity<>(headers);
    }
    
    //------------------------------------------------------------------------//
    // TESTS
    
    @Test
    public void listStudentsWhenTokenIsIncorrectShouldReturnStatusCode403 ()
    {
        ResponseEntity<String> entity = testRestTemplate.exchange ("/v1/protected/students/", HttpMethod.GET, wrongHeader, String.class);
        Assertions.assertEquals (entity.getStatusCodeValue (), 403);
    }
    
    @Test
    public void getStudentsByIdWhenTokenIsIncorrectShouldReturnStatusCode403 ()
    {
    	ResponseEntity<String> entity = testRestTemplate.exchange ("/v1/protected/students/20", HttpMethod.GET, wrongHeader, String.class);
        Assertions.assertEquals (entity.getStatusCodeValue (), 403);
    }
    
    @Test
    public void listStudentsWhenTokenIsCorrectShouldReturnStatusCode200 ()
    {
    	ResponseEntity<String> entity = testRestTemplate.exchange ("/v1/protected/students/", HttpMethod.GET, protectedHeader, String.class);
        Assertions.assertEquals (entity.getStatusCodeValue (), 200);
    }
    
    @Test
    public void getStudentsByIdWhenTokenIsCorrectShouldReturnStatusCode404 ()
    {
    	ResponseEntity<Student> entity = testRestTemplate.exchange ("/v1/protected/students/20", HttpMethod.GET, protectedHeader, Student.class);
        Assertions.assertEquals (entity.getStatusCodeValue (), 404);
    }
    
    @Test
    public void getStudentsByIdWhenTokenIsCorrectAndStudentDoesNotExistShouldReturnStatusCode404 ()
    {
    	ResponseEntity<Student> entity = testRestTemplate.exchange ("/v1/protected/students/-1", HttpMethod.GET, protectedHeader, Student.class);
        Assertions.assertEquals (entity.getStatusCodeValue (), 404);
    }
    
    @Test
    public void deleteWhenUserHasRoleAdminAndStudentExistsShouldReturnStatusCode200 () throws Exception
    {
    	ResponseEntity<Student> entity = testRestTemplate.exchange ("/v1/admin/students/1", HttpMethod.DELETE, adminHeader, Student.class);
        Assertions.assertEquals (entity.getStatusCodeValue (), 200);
    }
    
    @Test
    public void deleteWhenUserHasRoleAdminAndStudentDoesNotExistsShouldReturnStatusCode404 () throws Exception
    {
    	String header = "Authorization";
    	String token = adminHeader.getHeaders ().get ("Authorization").get (0);
        BDDMockito.doNothing ().when (studentRepository).deleteById (-1L);
        mockMvc.perform (MockMvcRequestBuilders.delete ("/v1/admin/students/{id}", -1L).header (header, token))
               .andExpect (MockMvcResultMatchers.status ().isNotFound ());
    }
    
    @Test
    public void deleteWhenUserDoesNotHaveRoleAdminShouldReturnStatusCode403 () throws Exception
    {
    	String header = "Authorization";
    	String token = protectedHeader.getHeaders ().get ("Authorization").get (0);
        BDDMockito.doNothing ().when (studentRepository).deleteById (-1L);
        mockMvc.perform (MockMvcRequestBuilders.delete ("/v1/admin/students/{id}", -1L).header (header, token))
               .andExpect (MockMvcResultMatchers.status ().isForbidden ());
    }
    
    @Test
    public void createWhenNameIsNullShouldReturnStatusCode400BadRequest ()
    {
        Student student = new Student (3L, null, "sam@email.com");
        BDDMockito.when (studentRepository.save (student)).thenReturn (student);
        HttpEntity<Student> httpEntity = new HttpEntity<>(student, adminHeader.getHeaders ());
        ResponseEntity<Student> response = testRestTemplate.exchange ("/v1/admin/students/", HttpMethod.POST, httpEntity, Student.class);
        Assertions.assertEquals (response.getStatusCodeValue (), 400);
    }
    
    @Test
    public void createShouldPersistDataAndReturnStatusCode201 ()
    {
        Student student = new Student (3L, "Sam", "sam@email.com");
        BDDMockito.when (studentRepository.save (student)).thenReturn (student);
        HttpEntity<Student> httpEntity = new HttpEntity<>(student, adminHeader.getHeaders ());
        ResponseEntity<Student> response = testRestTemplate.exchange ("/v1/admin/students/", HttpMethod.POST, httpEntity, Student.class);
        Assertions.assertEquals (response.getStatusCodeValue (), 201);    
        Assertions.assertNotEquals (response.getBody ().getId (), 0);    
    }
}
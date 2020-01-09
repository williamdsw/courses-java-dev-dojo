package com.williamdsw.springbootessentials;

import com.williamdsw.springbootessentials.model.Student;
import com.williamdsw.springbootessentials.repository.StudentRepository;
import java.util.Arrays;
import java.util.List;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestClass;
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
public class StudentEndpointTest
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    @Autowired 
    private TestRestTemplate testRestTemplate;
    
    @LocalServerPort    // Retorna porta local do server
    private int port;
    
    @MockBean   // Nao vai alterar os dados reais do BD
    private StudentRepository studentRepository;
    
    @Autowired
    private MockMvc mockMvc;
    
    //------------------------------------------------------------------------//
    // HELPER FUNCTIONS
    
    @Before ("true")
    public void setup ()
    {
        System.out.println ("setup...");
        Student student = new Student (20L, "Kerry King", "kerry@slayer.com");
        BDDMockito.when (studentRepository.findById (student.getId ()).get ()).thenReturn (student);
    }
    
    //------------------------------------------------------------------------//
    // TESTS
    
    @Test
    public void listStudentsWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401 ()
    {
        testRestTemplate = testRestTemplate.withBasicAuth ("1", "1");
        ResponseEntity<String> entity = testRestTemplate.getForEntity ("/v1/protected/students/", String.class);
        Assertions.assertEquals (entity.getStatusCodeValue (), 401);
        System.out.println ("Local Server Port: " + port);
    }
    
    //@Test
    public void getStudentsByIdWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401 ()
    {
        testRestTemplate = testRestTemplate.withBasicAuth ("1", "1");
        ResponseEntity<String> entity = testRestTemplate.getForEntity ("/v1/protected/students/10", String.class);
        Assertions.assertEquals (entity.getStatusCodeValue (), 401);
    }
    
    //@Test
    public void listStudentsWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200 ()
    {
        Student student1 = new Student (1L, "Kerry King", "kerry@slayer.com");
        Student student2 = new Student (2L, "Jeff Hanneman", "jeff@slayer.com");
        List<Student> students = Arrays.asList (student1, student2);
        
        testRestTemplate = testRestTemplate.withBasicAuth ("dave", "devdojo");
        BDDMockito.when (studentRepository.findAll ()).thenReturn (students);
        ResponseEntity<String> entity = testRestTemplate.getForEntity ("/v1/protected/students/", String.class);
        Assertions.assertEquals (entity.getStatusCodeValue (), 200);
    }
    
    //@Test
    public void getStudentsByIdWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200 ()
    {
        testRestTemplate = testRestTemplate.withBasicAuth ("dave", "devdojo");
        Student student = new Student (20L, "Kerry King", "kerry@slayer.com");
        BDDMockito.when (studentRepository.findById (student.getId ()).get ()).thenReturn (student);
        ResponseEntity<String> entity = testRestTemplate.getForEntity ("/v1/protected/students/{id}", String.class, student.getId ());
        Assertions.assertEquals (entity.getStatusCodeValue (), 200);
    }
    
    //@Test
    public void getStudentsByIdWhenUsernameAndPasswordAreCorrectAndStudentDoesNotExistShouldReturnStatusCode404 ()
    {
        testRestTemplate = testRestTemplate.withBasicAuth ("dave", "devdojo");
        ResponseEntity<String> entity = testRestTemplate.getForEntity ("/v1/protected/students/{id}", String.class, -1);
        Assertions.assertEquals (entity.getStatusCodeValue (), 404);
    }
    
    //@Test
    //@WithMockUser (username = "dave", password = "devdojo", roles = { "USER", "ADMIN" })
    public void deleteWhenUserHasRoleAdminAndStudentExistsShouldReturnStatusCode200 () throws Exception
    {
        BDDMockito.doNothing ().when (studentRepository).deleteById (1L);
        //testRestTemplate = testRestTemplate.withBasicAuth ("dave", "devdojo");
        //ResponseEntity<String> exchange = testRestTemplate.exchange ("/v1/admin/students/{id}", HttpMethod.DELETE, null, String.class, 20L);
        //Assertions.assertEquals (exchange.getStatusCodeValue (), 200);
        mockMvc.perform (MockMvcRequestBuilders.delete ("/v1/admin/students/{id}", 1L))
                .andExpect (MockMvcResultMatchers.status ().isOk ());
    }
    
    //@Test
    //@WithMockUser (username = "dave", password = "devdojo", roles = { "USER", "ADMIN" })
    public void deleteWhenUserHasRoleAdminAndStudentDoesNotExistsShouldReturnStatusCode404 () throws Exception
    {
        BDDMockito.doNothing ().when (studentRepository).deleteById (-1L);
        //testRestTemplate = testRestTemplate.withBasicAuth ("dave", "devdojo");
        //ResponseEntity<String> exchange = testRestTemplate.exchange ("/v1/admin/students/{id}", HttpMethod.DELETE, null, String.class, -1L);
        //Assertions.assertEquals (exchange.getStatusCodeValue (), 404);
        mockMvc.perform (MockMvcRequestBuilders.delete ("/v1/admin/students/{id}", -1L))
               .andExpect (MockMvcResultMatchers.status ().isNotFound ());
    }
    
    @Test
    @WithMockUser (username = "dave", password = "devdojo", roles = { "USER" })
    public void deleteWhenUserDoesNotHaveRoleAdminShouldReturnStatusCode403 () throws Exception
    {
        BDDMockito.doNothing ().when (studentRepository).deleteById (-1L);
        //testRestTemplate = testRestTemplate.withBasicAuth ("dave", "devdojo");
        //ResponseEntity<String> exchange = testRestTemplate.exchange ("/v1/admin/students/{id}", HttpMethod.DELETE, null, String.class, -1L);
        //Assertions.assertEquals (exchange.getStatusCodeValue (), 404);
        mockMvc.perform (MockMvcRequestBuilders.delete ("/v1/admin/students/{id}", -1L))
               .andExpect (MockMvcResultMatchers.status ().isForbidden ());
    }
    
    //@Test
    public void createWhenNameIsNullShouldReturnStatusCode400BadRequest ()
    {
        Student student = new Student (3L, null, "sam@email.com");
        BDDMockito.when (studentRepository.save (student)).thenReturn (student);
        ResponseEntity<String> post = testRestTemplate.postForEntity ("/v1/admin/students", student, String.class);
        Assertions.assertEquals (post.getStatusCodeValue (), 400);        
    }
    
    //@Test
    public void createShouldPersistDataAndReturnStatusCode201 ()
    {
        Student student = new Student (3L, "Sam", "sam@email.com");
        BDDMockito.when (studentRepository.save (student)).thenReturn (student);
        ResponseEntity<Student> post = testRestTemplate.postForEntity ("/v1/admin/students", student, Student.class);
        Assertions.assertEquals (post.getStatusCodeValue (), 201);    
        Assertions.assertNotEquals (post.getBody ().getId (), 0);    
    }
    
    //------------------------------------------------------------------------//
    // INNER CLASSES
    
    @TestConfiguration
    private static class Config
    {
        @Bean
        public RestTemplateBuilder restTemplateBuilder ()
        {
            return new RestTemplateBuilder ().basicAuthentication ("dave", "devdojo");
        }
    }
}
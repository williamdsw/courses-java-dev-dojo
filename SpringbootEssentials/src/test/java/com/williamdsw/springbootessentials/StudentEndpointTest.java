package com.williamdsw.springbootessentials;

import com.williamdsw.springbootessentials.model.Student;
import com.williamdsw.springbootessentials.repository.StudentRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author William
 */

@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
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
    // TESTS
    
    @Test
    public void listStudentsWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401 ()
    {
        testRestTemplate = testRestTemplate.withBasicAuth ("1", "1");
        ResponseEntity<String> entity = testRestTemplate.getForEntity ("/v1/protected/students/", String.class);
        Assertions.assertEquals (entity.getStatusCodeValue (), 401);
        System.out.println ("Local Server Port: " + port);
    }
    
    @Test
    public void getStudentsByIdWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401 ()
    {
        testRestTemplate = testRestTemplate.withBasicAuth ("1", "1");
        ResponseEntity<String> entity = testRestTemplate.getForEntity ("/v1/protected/students/10", String.class);
        Assertions.assertEquals (entity.getStatusCodeValue (), 401);
    }
    
    @Test
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
    
    @Test
    public void getStudentsByIdWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200 ()
    {
        testRestTemplate = testRestTemplate.withBasicAuth ("dave", "devdojo");
        Student student = new Student (20L, "Kerry King", "kerry@slayer.com");
        BDDMockito.when (studentRepository.findById (student.getId ()).get ()).thenReturn (student);
        ResponseEntity<String> entity = testRestTemplate.getForEntity ("/v1/protected/students/{id}", String.class, student.getId ());
        Assertions.assertEquals (entity.getStatusCodeValue (), 200);
    }
    
    @Test
    public void getStudentsByIdWhenUsernameAndPasswordAreCorrectAndStudentDoesNotExistShouldReturnStatusCode404 ()
    {
        testRestTemplate = testRestTemplate.withBasicAuth ("dave", "devdojo");
        ResponseEntity<String> entity = testRestTemplate.getForEntity ("/v1/protected/students/{id}", String.class, -1);
        Assertions.assertEquals (entity.getStatusCodeValue (), 404);
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
package com.williamdsw.springbootessentials.javaclient;

import com.williamdsw.springbootessentials.model.Student;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author William
 */
public class JavaSpringClientTest
{
    public static void main (String[] args)
    {
        getStudent ();
        getStudents ();
    }
    
    //------------------------------------------------------------------------//
    // HELPER FUNCTIONS

    private static void getStudent () throws RestClientException
    {
        String rootUri = "http://localhost:8080/v1/protected/students";
        String username = "dave";
        String password = "devdojo";
        
        // Conexao e recupera o objeto
        RestTemplate restTemplate = new RestTemplateBuilder ().rootUri (rootUri).basicAuthentication (username, password).build ();
        Student student = restTemplate.getForObject ("/{id}", Student.class, 10);
        ResponseEntity<Student> entity = restTemplate.getForEntity ("/{id}", Student.class, 10);
        
        System.out.println (student);
        System.out.println (entity);
    }
    
    private static void getStudents () throws RestClientException
    {
        String rootUri = "http://localhost:8080/v1/protected/students";
        String username = "dave";
        String password = "devdojo";
        
        // Conexao e recupera o objeto
        RestTemplate restTemplate = new RestTemplateBuilder ().rootUri (rootUri).basicAuthentication (username, password).build ();
        Student[] students = restTemplate.getForObject ("/", Student[].class);
        ParameterizedTypeReference<List<Student>> reference = new ParameterizedTypeReference<List<Student>> (){};
        ResponseEntity<List<Student>> entity = restTemplate.exchange ("/", HttpMethod.GET, null, reference);
        
        System.out.println (Arrays.toString (students));
        System.out.println (entity.getBody ());
    }
}
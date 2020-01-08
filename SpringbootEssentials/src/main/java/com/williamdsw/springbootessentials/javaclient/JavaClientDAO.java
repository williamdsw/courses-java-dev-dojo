package com.williamdsw.springbootessentials.javaclient;

import com.williamdsw.springbootessentials.model.PageableResponse;
import com.williamdsw.springbootessentials.model.Student;
import java.util.List;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author William
 */
public class JavaClientDAO
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    private final String rootUri = "http://localhost:8080/v1/protected/students";
    private final String rootUriAdmin = "http://localhost:8080/v1/admin/students";
    private final String username = "dave";
    private final String password = "devdojo";

    // Conexao e recupera o objeto
    RestTemplate restTemplate = new RestTemplateBuilder ().rootUri (rootUri).basicAuthentication (username, password).build ();
    RestTemplate restTemplateAdmin = new RestTemplateBuilder ().rootUri (rootUriAdmin).basicAuthentication (username, password).build ();
    
    //------------------------------------------------------------------------//
    // HELPER FUNCTIONS
    
    private static HttpHeaders createJsonHeader ()
    {
        HttpHeaders headers = new HttpHeaders ();
        headers.setContentType (MediaType.APPLICATION_JSON);
        return headers;
    }
    
    public Student findById (Long id)
    {
        Student student = restTemplate.getForObject ("/{id}", Student.class, id);
        return student;
    }
    
    public List<Student> listAll ()
    {
        ParameterizedTypeReference<PageableResponse<Student>> reference = new ParameterizedTypeReference<PageableResponse<Student>> (){};
        ResponseEntity<PageableResponse<Student>> entity = restTemplate.exchange ("/", HttpMethod.GET, null, reference);
        List<Student> students = entity.getBody ().getContent ();
        return students;
    }
    
    public Student save (Student student)
    {
        HttpEntity<?> entity = new HttpEntity<>(student, createJsonHeader ());
        ResponseEntity<Student> post = restTemplateAdmin.exchange ("/", HttpMethod.POST, entity, Student.class);
        return post.getBody ();
    }
   
}
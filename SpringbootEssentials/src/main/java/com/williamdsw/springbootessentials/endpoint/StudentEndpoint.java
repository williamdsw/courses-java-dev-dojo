package com.williamdsw.springbootessentials.endpoint;

import com.williamdsw.springbootessentials.error.CustomErrorType;
import com.williamdsw.springbootessentials.model.Student;
import com.williamdsw.springbootessentials.repository.StudentRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author William
 */

@RestController
@RequestMapping (path = "/students")
public class StudentEndpoint
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    private final StudentRepository studentDAO;

    //------------------------------------------------------------------------//
    // CONSTRUCTOR
    
    @Autowired
    public StudentEndpoint (StudentRepository studentDao)
    {
        this.studentDAO = studentDao;
    }
    
    //------------------------------------------------------------------------//
    // ENDPOINTS
    
    @GetMapping
    public ResponseEntity<?> listAll ()
    {
        return new ResponseEntity<>(studentDAO.findAll (), HttpStatus.OK);
    }
    
    @GetMapping (path = "/{id}")
    public ResponseEntity<?> getStudentById (@PathVariable ("id") Long id)
    {
        Optional<Student> student = studentDAO.findById (id);
        if (student == null)
        {
            return new ResponseEntity<>(new CustomErrorType ("Student not found"), HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<?> save (@RequestBody Student student)
    {
        return new ResponseEntity<> (studentDAO.save (student), HttpStatus.OK);
    }
    
    @PutMapping
    public ResponseEntity<?> update (@RequestBody Student student)
    {
        studentDAO.save (student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping (path = "/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id)
    {
        studentDAO.deleteById (id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
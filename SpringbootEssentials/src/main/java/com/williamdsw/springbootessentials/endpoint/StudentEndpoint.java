package com.williamdsw.springbootessentials.endpoint;

import com.williamdsw.springbootessentials.error.CustomErrorType;
import com.williamdsw.springbootessentials.model.Student;
import com.williamdsw.springbootessentials.util.DateUtil;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author William
 */

@RestController
@RequestMapping (path = "/students")
public class StudentEndpoint
{    
    //------------------------------------------------------------------------//
    // ENDPOINTS
    
    @GetMapping
    public ResponseEntity<?> listAll ()
    {
        return new ResponseEntity<>(Student.getStudents (), HttpStatus.OK);
    }
    
    @GetMapping ("/{id}")
    public ResponseEntity<?> getStudentById (@PathVariable ("id") Integer id)
    {
        Student student = new Student ();
        student.setId (id);
        
        int index = Student.getStudents ().indexOf (student);
        if (index == -1)
        {
            return new ResponseEntity<>(new CustomErrorType ("Student not found"), HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(Student.getStudents ().get (index), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<?> save (@RequestBody Student student)
    {
        Student.getStudents ().add (student);
        return new ResponseEntity<> (student, HttpStatus.OK);
    }
    
    @PutMapping
    public ResponseEntity<?> update (@RequestBody Student student)
    {
        Student.getStudents ().remove (student);
        Student.getStudents ().add (student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping
    public ResponseEntity<?> delete (@RequestBody Student student)
    {
        Student.getStudents ().remove (student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
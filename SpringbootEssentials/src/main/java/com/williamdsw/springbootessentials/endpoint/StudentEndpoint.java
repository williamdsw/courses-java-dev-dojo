package com.williamdsw.springbootessentials.endpoint;

import com.williamdsw.springbootessentials.model.Student;
import java.util.Arrays;
import java.util.List;
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
    
    @RequestMapping (method = RequestMethod.GET, path = "/list")
    public List<Student> listAll ()
    {
        return Arrays.asList (new Student ("Deku"), new Student ("Todoroki"));
    }
}
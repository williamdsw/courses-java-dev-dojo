package com.williamdsw.springbootessentials.endpoint;

import com.williamdsw.springbootessentials.model.Student;
import com.williamdsw.springbootessentials.util.DateUtil;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    // FIELDS
    
    @Autowired private DateUtil dateUtil;
    
    //------------------------------------------------------------------------//
    // ENDPOINTS
    
    @RequestMapping (method = RequestMethod.GET, path = "/list")
    public List<Student> listAll ()
    {
        System.out.println (dateUtil.formatLocalDateTimeToDatabaseStyle (LocalDateTime.now ()));
        return Arrays.asList (new Student ("Deku"), new Student ("Todoroki"));
    }
}
package com.williamdsw.springbootessentials.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author William
 */
@ResponseStatus (HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    private static final long serialVersionUID = 1L;

    //------------------------------------------------------------------------//
    // CONSTRUCTOR
    
    public ResourceNotFoundException (String string)
    {
        super (string);
    }
}
package com.williamdsw.springbootessentials.error;

/**
 * @author William
 */
public class CustomErrorType
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    private String message;

    //------------------------------------------------------------------------//
    // CONSTRUCTORS
    
    public CustomErrorType (String message)
    {
        this.message = message;
    }

    //------------------------------------------------------------------------//
    // GETTERS
    
    public String getMessage ()
    {
        return message;
    }
}
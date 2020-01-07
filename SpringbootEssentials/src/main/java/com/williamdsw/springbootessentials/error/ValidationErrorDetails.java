package com.williamdsw.springbootessentials.error;

/**
 * @author William
 */
public class ValidationErrorDetails extends ErrorDetails
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    private String field;
    private String fieldMessage;

    //------------------------------------------------------------------------//
    // CONSTRUCTORS
    
    public ValidationErrorDetails () {}
    public ValidationErrorDetails (String field, String fieldMessage, String title, Integer status, String detail, Long timestamp, String message)
    {
        super (title, status, detail, timestamp, message);
        this.field = field;
        this.fieldMessage = fieldMessage;
    }

    //------------------------------------------------------------------------//
    // GETTERS / SETTERS
    
    public String getField ()
    {
        return field;
    }

    public void setField (String field)
    {
        this.field = field;
    }

    public String getFieldMessage ()
    {
        return fieldMessage;
    }

    public void setFieldMessage (String fieldMessage)
    {
        this.fieldMessage = fieldMessage;
    }
}
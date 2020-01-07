package com.williamdsw.springbootessentials.error;

/**
 * @author William
 */
public class ErrorDetails
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    private String title;
    private Integer status;
    private String detail;
    private Long timestamp;
    private String message;

    //------------------------------------------------------------------------//
    // CONSTRUCTORS
    
    public ErrorDetails () {}
    public ErrorDetails (String title, Integer status, String detail, Long timestamp, String message)
    {
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.timestamp = timestamp;
        this.message = message;
    }
    
    //------------------------------------------------------------------------//
    // GETTERS / SETTERS

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public Integer getStatus ()
    {
        return status;
    }

    public void setStatus (Integer status)
    {
        this.status = status;
    }

    public String getDetail ()
    {
        return detail;
    }

    public void setDetail (String detail)
    {
        this.detail = detail;
    }

    public Long getTimestamp ()
    {
        return timestamp;
    }

    public void setTimestamp (Long timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }
}
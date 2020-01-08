package com.williamdsw.springbootessentials.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author William
 */
@Entity
public class Student extends AbstractEntity
{
    //------------------------------------------------------------------------//
    // FIELDS
   
    @NotEmpty (message = "Name is required")
    private String name;
    
    @NotEmpty (message = "Email is required")
    @Email (message = "Invalid email")
    private String email;
    
    //------------------------------------------------------------------------//
    // CONSTRUCTORS
   
    public Student () {}
    public Student (String name, String email)
    {
        this.name = name;
        this.email = email;
    } 

    //------------------------------------------------------------------------//
    // GETTERS / SETTERS

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }
    
    //------------------------------------------------------------------------//
    // OVERRIDED FUNCTIONS

    @Override
    public String toString ()
    {
        return String.format ("%s - %s", this.getName (), this.getEmail ());
    }
}
package com.williamdsw.springbootessentials.model;

import javax.persistence.Entity;

/**
 * @author William
 */
@Entity
public class Student extends AbstractEntity
{
    //------------------------------------------------------------------------//
    // FIELDS
   
    private String name;
    
    //------------------------------------------------------------------------//
    // CONSTRUCTORS
   
    
    public Student () {}
    public Student (String name)
    {
        this.name = name;
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
}
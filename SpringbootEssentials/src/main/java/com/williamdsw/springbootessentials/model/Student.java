package com.williamdsw.springbootessentials.model;

/**
 * @author William
 */

public class Student
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
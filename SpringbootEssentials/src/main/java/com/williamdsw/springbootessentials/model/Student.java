package com.williamdsw.springbootessentials.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author William
 */

public class Student
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    private Integer id;
    private String name;
    private static List<Student> students = new ArrayList<> ();
    
    //------------------------------------------------------------------------//
    // CONSTRUCTORS
    
    // Inicializador static que roda apenas quando a classe e carregada na memora
    static 
    {
        studentRepository ();
    }
    
    public Student () {}
    
    public Student (Integer id, String name)
    {
        this (name);
        this.id = id;
    }
    
    public Student (String name)
    {
        this.name = name;
    }

    //------------------------------------------------------------------------//
    // GETTERS / SETTERS

    public Integer getId ()
    {
        return id;
    }

    public void setId (Integer id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public static List<Student> getStudents ()
    {
        return students;
    }
    
    //------------------------------------------------------------------------//
    // OVERRIDED FUNCTIONS

    @Override
    public int hashCode ()
    {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode (this.id);
        return hash;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass () != obj.getClass ())
        {
            return false;
        }
        final Student other = (Student) obj;
        if (!Objects.equals (this.id, other.id))
        {
            return false;
        }
        return true;
    }
   
    //------------------------------------------------------------------------//
    // HELPER FUNCTIONS
    
    private static void studentRepository ()
    {
        Student deku = new Student (1, "Deku");
        Student todoroki = new Student (2, "Todoroki");
        students.addAll (Arrays.asList (deku, todoroki));
    }
}
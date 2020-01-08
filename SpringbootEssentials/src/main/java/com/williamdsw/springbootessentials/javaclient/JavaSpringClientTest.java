package com.williamdsw.springbootessentials.javaclient;

import com.williamdsw.springbootessentials.model.Student;

/**
 * @author William
 */
public class JavaSpringClientTest
{
    public static void main (String[] args)
    {
        Student student = new Student ("Marty Friedman", "marty@email.com");
        JavaClientDAO dao = new JavaClientDAO ();
        
        // Conexoes
        System.out.println (dao.findById (20L));
        System.out.println (dao.listAll ());
        System.out.println (dao.save (student));
    }
}
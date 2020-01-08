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
        Student updatedStudent = new Student ("Dave Lombardo", "lombardo@email.com");
        updatedStudent.setId (40L);
        JavaClientDAO dao = new JavaClientDAO ();
        
        // Conexoes
        System.out.println (dao.findById (555L));
        System.out.println (dao.listAll ());
        System.out.println (dao.save (student));
        dao.update (updatedStudent);
        dao.delete (40L);
    }
}
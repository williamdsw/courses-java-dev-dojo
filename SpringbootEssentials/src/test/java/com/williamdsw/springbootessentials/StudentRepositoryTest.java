package com.williamdsw.springbootessentials;

import com.williamdsw.springbootessentials.model.Student;
import com.williamdsw.springbootessentials.repository.StudentRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author William
 */

@ExtendWith (SpringExtension.class)
@DataJpaTest
//@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    @Autowired private StudentRepository studentRepository;
    
    //------------------------------------------------------------------------//
    // TESTS
    
    // Testa insert
    @Test
    public void createShouldPersistData ()
    {
        Student student = new Student ("Dave Mustaine", "dave_mustaine@email.com");
        studentRepository.save (student);
        Assertions.assertNotNull (student.getId ());
        Assertions.assertEquals (student.getName (), "Dave Mustaine");
        Assertions.assertEquals (student.getEmail (), "dave_mustaine@email.com");
    }

    // Testa delete
    @Test
    public void deleteShouldRemoveData ()
    {
        Student student = new Student ("Dave Mustaine", "dave_mustaine@email.com");
        studentRepository.save (student);
        studentRepository.delete (student);
        Assertions.assertTrue (!studentRepository.findById (student.getId ()).isPresent ());
    }
    
    // Testa update
    @Test
    public void updateShouldChangeAndPersistData ()
    {
        Student student = new Student ("Dave Mustaine", "dave_mustaine@email.com");
        studentRepository.save (student);
        student.setName ("James Hetfield");
        student.setEmail ("james_hetfield@email.com");
        studentRepository.save (student);
        Optional<Student> optional = studentRepository.findById (student.getId ());
        student = optional.get ();
        Assertions.assertEquals (student.getName (), "James Hetfield");
        Assertions.assertEquals (student.getEmail (), "james_hetfield@email.com");
    }
    
    // Testa busca
    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase ()
    {
        Student student1 = new Student ("Dave Mustaine", "dave_mustaine@email.com");
        Student student2 = new Student ("dave mustaine", "dave_mustaine@email.com");
        studentRepository.save (student1);
        studentRepository.save (student2);
        List<Student> students = studentRepository.findByNameIgnoreCaseContaining ("dave");
        Assertions.assertEquals (students.size (), 2);
    }
    
    // Testa insert com name null
    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException ()
    {
        Student student = new Student (null, "dave_mustaine@email.com");
        Assertions.assertThrows (ConstraintViolationException.class, () -> studentRepository.save (student));
    }
    
    // Testa insert com email null
    @Test
    public void createWhenEmailIsNullShouldThrowConstraintViolationException ()
    {
        Student student = new Student ("Dave Mustaine", null);
        Assertions.assertThrows (ConstraintViolationException.class, () -> studentRepository.save (student));
    }
    
    // Testa insert com email invalido
    @Test
    public void createWhenEmailIsNotValidShouldThrowConstraintViolationException ()
    {
        Student student = new Student ("Dave Mustaine", "INVALID");
        Assertions.assertThrows (ConstraintViolationException.class, () -> studentRepository.save (student));
    }
}
package com.williamdsw.springbootessentials.repository;

import com.williamdsw.springbootessentials.model.Student;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author William
 */

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>
{
    public List<Student> findByName (String name);
}
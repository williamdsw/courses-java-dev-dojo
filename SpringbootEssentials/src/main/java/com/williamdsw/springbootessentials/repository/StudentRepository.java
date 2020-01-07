package com.williamdsw.springbootessentials.repository;

import com.williamdsw.springbootessentials.model.Student;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author William
 */

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long>
{
    public List<Student> findByNameIgnoreCaseContaining (String name);
}
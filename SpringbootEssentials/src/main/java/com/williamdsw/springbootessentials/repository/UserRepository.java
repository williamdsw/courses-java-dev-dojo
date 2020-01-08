package com.williamdsw.springbootessentials.repository;

import com.williamdsw.springbootessentials.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author William
 */

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>
{
    public User findByUsername (String username);
}
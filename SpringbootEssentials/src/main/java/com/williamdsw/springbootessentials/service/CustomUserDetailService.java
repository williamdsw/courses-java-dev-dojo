package com.williamdsw.springbootessentials.service;

import com.williamdsw.springbootessentials.model.User;
import com.williamdsw.springbootessentials.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author William
 */

@Service
public class CustomUserDetailService implements UserDetailsService
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    private final UserRepository userRepository;

    //------------------------------------------------------------------------//
    // CONSTRUCTOR
    
    @Autowired
    public CustomUserDetailService (UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    //------------------------------------------------------------------------//
    // OVERRIDED FUNCTIONS
    
    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername (username);
        user = Optional.ofNullable (user).orElseThrow (() -> new UsernameNotFoundException ("User not found!"));
        List<GrantedAuthority> authoritiesAdmin = AuthorityUtils.createAuthorityList ("ROLE_USER","ROLE_ADMIN");
        List<GrantedAuthority> authoritiesUser = AuthorityUtils.createAuthorityList ("ROLE_USER");
        return new org.springframework.security.core.userdetails.User (user.getUsername (), user.getPassword (), user.getIsAdmin () ? authoritiesAdmin : authoritiesUser);
    }
}
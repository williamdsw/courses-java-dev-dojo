package com.williamdsw.springbootessentials.config;

import com.williamdsw.springbootessentials.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author William
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity (prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    @Autowired private CustomUserDetailService customUserDetailService;
    
    //------------------------------------------------------------------------//
    // OVERRIDED FUNCTIONS

    @Override
    protected void configure (HttpSecurity http) throws Exception
    {
        http.authorizeRequests ()
            .antMatchers ("/*/protected/**").hasAnyRole ("USER")
            .antMatchers ("/*/admin/**").hasAnyRole ("ADMIN")
            .and ()
            .httpBasic ()
            .and ()
            .csrf ().disable ();
    }
    
    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService (customUserDetailService).passwordEncoder (new BCryptPasswordEncoder ());
    }
}
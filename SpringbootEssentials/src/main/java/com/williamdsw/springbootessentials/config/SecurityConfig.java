package com.williamdsw.springbootessentials.config;

import com.williamdsw.springbootessentials.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

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
    	CorsConfiguration corsConfiguration = new CorsConfiguration ().applyPermitDefaultValues ();
    	http.cors ()
    		.configurationSource (request -> corsConfiguration)
    		.and ().csrf ().disable ().authorizeRequests ()
    		.antMatchers(HttpMethod.GET, SecurityConstants.SIGN_UP_URL).permitAll ()
    		.antMatchers ("/*/protected/**").hasRole ("USER")
            .antMatchers ("/*/admin/**").hasRole ("ADMIN")
            .and ()
            .addFilter(new JWTAuthenticationFilter (this.authenticationManager ()))
            .addFilter(new JWTAuthorizationFilter(this.authenticationManager (), customUserDetailService));
    }
    
    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService (customUserDetailService).passwordEncoder (new BCryptPasswordEncoder ());
    }
}
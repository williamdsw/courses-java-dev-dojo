package com.williamdsw.springbootessentials.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author William
 */
public class PasswordEncoder
{
    public static void main (String[] args)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder ();
        System.out.println (passwordEncoder.encode ("metallica"));
    }
}
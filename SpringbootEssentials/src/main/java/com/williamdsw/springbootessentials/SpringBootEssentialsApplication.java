package com.williamdsw.springbootessentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @Configuration + @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication
public class SpringBootEssentialsApplication
{
    public static void main (String[] args)
    {
        SpringApplication.run (SpringBootEssentialsApplication.class, args);
    }
}
package com.williamdsw.springbootessentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan (basePackages = "com.williamdsw.springbootessentials.endpoint")
public class SpringBootEssentialsApplication
{
    public static void main (String[] args)
    {
        SpringApplication.run (SpringBootEssentialsApplication.class, args);
    }
}
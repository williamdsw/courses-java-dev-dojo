package com.williamdsw.springbootessentials.config;

import java.util.concurrent.TimeUnit;

public class SecurityConstants 
{
    //------------------------------------------------------------------------//
    // CONST
	
	public static final String HEADER_STRING = "Authorization";
	public static final String SECRET = "DevDojoFoda";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String SIGN_UP_URL = "/users/sign-up";
	public static final Long EXPIRATION_TIME = 86400000L;
	
	public static void main (String[] args) 
	{
		System.out.println (TimeUnit.MILLISECONDS.convert(1L, TimeUnit.DAYS));	
	}
}
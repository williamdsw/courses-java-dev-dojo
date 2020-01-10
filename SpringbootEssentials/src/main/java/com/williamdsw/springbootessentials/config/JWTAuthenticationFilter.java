package com.williamdsw.springbootessentials.config;

import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    //------------------------------------------------------------------------//
    // FIELDS
	
	private AuthenticationManager authenticationManager;

    //------------------------------------------------------------------------//
    // CONSTRUCTOR
	
	public JWTAuthenticationFilter (AuthenticationManager authenticationManager) 
	{
		super();
		this.authenticationManager = authenticationManager;
	}
	
    //------------------------------------------------------------------------//
    // OVERRIDED FUNCTIONS
	
	@Override
	public Authentication attemptAuthentication (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
	{
		try 
		{
			ObjectMapper mapper = new ObjectMapper ();
			com.williamdsw.springbootessentials.model.User user = mapper.readValue (request.getInputStream (), com.williamdsw.springbootessentials.model.User.class);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken (user.getUsername (), user.getPassword ());
			return this.authenticationManager.authenticate (token);
		} 
		catch (Exception exception)
		{
			throw new RuntimeException (exception);
		}
	}
	
	@Override
	protected void successfulAuthentication (HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException 
	{
		User user = (User) authResult.getPrincipal ();
		Date expiration = new Date (System.currentTimeMillis () + SecurityConstants.EXPIRATION_TIME);
		String token = Jwts.builder ()
						   .setSubject (user.getUsername ())
						   .setExpiration (expiration)
						   .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
						   .compact ();
		response.addHeader (SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
	}
}
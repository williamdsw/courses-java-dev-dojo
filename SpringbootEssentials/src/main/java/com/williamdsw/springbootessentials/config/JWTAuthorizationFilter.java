package com.williamdsw.springbootessentials.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.williamdsw.springbootessentials.service.CustomUserDetailService;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter
{
    //------------------------------------------------------------------------//
    // FIELDS
	
	private final CustomUserDetailService customUserDetailService;

    //------------------------------------------------------------------------//
    // CONSTRUCTOR
	
	public JWTAuthorizationFilter (AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService) 
	{
		super (authenticationManager);
		this.customUserDetailService = customUserDetailService;
	}
	
    //------------------------------------------------------------------------//
    // OVERRIDED FUNCTIONS
	
	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		String header = request.getHeader (SecurityConstants.HEADER_STRING);
		if (header == null || !header.startsWith (SecurityConstants.TOKEN_PREFIX))
		{
			SecurityContextHolder.getContext ().setAuthentication (null);
			chain.doFilter (request, response);
			return;
		}
		
		try 
		{
			UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken (request);
			SecurityContextHolder.getContext ().setAuthentication (authenticationToken);
		} 
		catch (Exception exception) 
		{
			SecurityContextHolder.getContext ().setAuthentication (null);
		}
		
		chain.doFilter (request, response);
	}
	
    //------------------------------------------------------------------------//
    // HELPER FUNCTIONS
	
	private UsernamePasswordAuthenticationToken getAuthenticationToken (HttpServletRequest request)
	{
		String token = request.getHeader (SecurityConstants.HEADER_STRING);
		if (token == null ) { return null; }
		
		String username = Jwts.parser ()
							  .setSigningKey (SecurityConstants.SECRET)
							  .parseClaimsJws (token.replace (SecurityConstants.TOKEN_PREFIX, ""))
							  .getBody ()
							  .getSubject ();
		UserDetails userDetails = customUserDetailService.loadUserByUsername (username);
		return (username != null ? new UsernamePasswordAuthenticationToken (username, null, userDetails.getAuthorities ()) : null);
	}
}
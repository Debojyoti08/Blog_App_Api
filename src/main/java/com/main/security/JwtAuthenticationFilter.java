package com.main.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService service;

	@Autowired
	private JwtTokenHelper helper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestToken = request.getHeader("Authorization");
		
		//Bearer
		
		System.out.println(requestToken);
		
		String username = null;
		String token = null;
		
		if (requestToken != null && requestToken.startsWith("Bearer "))
		{
			
			token = requestToken.substring(7);
			
			try {
				username = this.helper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get Jwt token");
			}
			catch (ExpiredJwtException e) {
				System.out.println("Token expired");
			}
			catch (MalformedJwtException e) {
				System.out.println("Invalid Jwt Exception");
			}
		} 
		
		else 
		{
			System.out.println("Jwt does not begin with Bearer");
		}
		
		//once token is received
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails user = this.service.loadUserByUsername(username);
			
			if (this.helper.validateToken(token, user))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
						UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
						.buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);	
			} else {
				System.out.println("Invalid Jwt");
			}
			
		}
		else 
		{
			System.out.println("Username or context is null");
		}
		
		filterChain.doFilter(request, response);
		
	}

}

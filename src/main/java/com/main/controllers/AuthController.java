package com.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.security.JwtAuthRequest;
import com.main.security.JwtAuthResponse;
import com.main.security.JwtTokenHelper;
import com.main.security.UserDetailService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper helper;
	
	@Autowired
	private UserDetailService service;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
	        @RequestBody JwtAuthRequest request) {

	    authenticate(request.getUsername(), request.getPassword());

	    UserDetails userDetails =
	            service.loadUserByUsername(request.getUsername());

	    String token = helper.generateToken(userDetails);

	    JwtAuthResponse response = new JwtAuthResponse();
	    response.setToken(token);

	    return ResponseEntity.ok(response);
	}


	private void authenticate(String username, String password) {
	    UsernamePasswordAuthenticationToken authToken =
	            new UsernamePasswordAuthenticationToken(username, password);

	    try {
	        authenticationManager.authenticate(authToken);
	    }
	    catch (DisabledException e) {
	        throw new RuntimeException("User is disabled");
	    }
	    catch (org.springframework.security.authentication.BadCredentialsException e) {
	        throw new RuntimeException("Invalid username or password");
	    }
	}


}

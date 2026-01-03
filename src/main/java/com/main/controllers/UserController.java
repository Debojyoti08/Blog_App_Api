package com.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.payloads.ApiResponse;
import com.main.payloads.UserDTO;
import com.main.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userdto) {
		UserDTO createuser = this.userservice.createUser(userdto);
		return new ResponseEntity<>(createuser, HttpStatus.CREATED);
	}
	
	@PutMapping("/{userid}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userdto, @PathVariable Integer userid) {
		UserDTO updated = this.userservice.updateUser(userdto, userid);
		return ResponseEntity.ok(updated);
	}
	
	//Admin
	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("/{userid}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userid) {
		this.userservice.deleteUser(userid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return ResponseEntity.ok(this.userservice.getAllUsers());
	}
	
	@GetMapping("/{userid}")
	public ResponseEntity<UserDTO> getSingleUser(@PathVariable Integer userid) {
		return ResponseEntity.ok(this.userservice.getUserById(userid));
	}

}

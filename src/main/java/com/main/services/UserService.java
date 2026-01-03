package com.main.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.User;
import com.main.exceptions.ResourceNotFoundException;
import com.main.payloads.UserDTO;
import com.main.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	public UserDTO createUser(UserDTO userdto) {
		User user = this.dtotoUser(userdto);
		User savedUser = this.userRepo.save(user);
		return this.usertoDTO(savedUser);
		
	}
	
	public UserDTO updateUser(UserDTO userdto, Integer id) {
		User user = this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setAbout(userdto.getAbout());
		user.setPassword(userdto.getPassword());
		
		User updateduser = this.userRepo.save(user);
		UserDTO userdto1 = this.usertoDTO(updateduser);
		
		return userdto1;
	}
	
	public UserDTO getUserById(Integer id) {
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		return this.usertoDTO(user);
	}
	
	
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDTO> userdtos = users.stream().map(user->this.usertoDTO(user)).collect(Collectors.toList());
		return userdtos;
	}
	
	public void deleteUser(Integer id) {
		User user = this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		this.userRepo.delete(user);
	}
	
	private User dtotoUser(UserDTO userDto) {
		User user = this.modelmapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		return user;
	}
	
	private UserDTO usertoDTO(User user) {
		UserDTO userdto = this.modelmapper.map(user, UserDTO.class);
		
//		userdto.setId(user.getId());
//		userdto.setName(user.getName());
//		userdto.setEmail(user.getEmail());
//		userdto.setAbout(user.getAbout());
//		userdto.setPassword(user.getPassword());
		
		return userdto;
	}

}

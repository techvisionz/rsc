package com.tz.rsc.controllers;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tz.rsc.entities.User;
import com.tz.rsc.repository.UserRepository;

@RestController

@RequestMapping(value = "/users")

public class UserController
{
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
	{
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@GetMapping("/all")
	public List<User> all()
	{
		return (List<User>) userRepository.findAll();
	}

	@PostMapping("/signup")
	public String signUp(@RequestBody User user)
	{
		if(userRepository.findByUsername(user.getUsername()) != null) {
			return "{\"message\":\"User already exists\"}";
		}
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "{\"message\":\"success\"}";
	}

}
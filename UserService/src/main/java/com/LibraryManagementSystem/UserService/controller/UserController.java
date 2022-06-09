package com.LibraryManagementSystem.UserService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LibraryManagementSystem.UserService.entity.User;
import com.LibraryManagementSystem.UserService.repository.UserRepository;
import com.LibraryManagementSystem.UserService.service.UserService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/userService")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String checkUserService()
	{
		return "User Service Available";
	}
	
	
	@GetMapping("/addToUser/{firstname}/{lastname}/{email}/{gender}/{password}/{usertype}/{userTypeFlag}")
	public String addUserToDB(@PathVariable("firstname") String firstName,
			@PathVariable("lastname") String lastName,
			@PathVariable("email") String email,
			@PathVariable("gender") char gender,
			@PathVariable("password") String passwd,
			@PathVariable("usertype") String usertype,
			@PathVariable("userTypeFlag") int userTypeFlag)
	{
		
		if(!userService.findByEmail(email))
		{
			User user = new User();
			user.setUsername(userService.getUsername(firstName,lastName));
			user.setEmail(email);
			user.setGender(gender);
			user.setPassword(passwd);
			user.setUserType(usertype);
			user.setFlag(userTypeFlag);
			userRepository.save(user);
			return "true";	
		}
		else
		{
			return "false";
		}					
	}
	
	@GetMapping("/checkUser/{username}/{password}")
	public int checkUserCredentials(@PathVariable("username") String username,
			@PathVariable("password") String pswd)
	{
		User user = userService.checkUser(username,pswd);
		if(user != null)
		{
			return user.getFlag();
		}
		else
		{
			return -1;
		}		
	}
	
	@GetMapping("/getUserId/{currentUserSession}")
	public int getUserID(@PathVariable("currentUserSession") String currentUserSession)
	{
		User user = userService.findUserId(currentUserSession);
		if(user != null)
		{
			return user.getUserid();
		}
		return -1;
	}
	
	
	
	
}

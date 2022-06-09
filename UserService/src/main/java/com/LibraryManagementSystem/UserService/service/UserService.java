package com.LibraryManagementSystem.UserService.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LibraryManagementSystem.UserService.entity.User;
import com.LibraryManagementSystem.UserService.repository.UserRepository;

@Service
public class UserService {

	
	@Autowired
	UserRepository userRepository;
	
	public String getUsername(String firstName, String lastName) {
		
		return firstName + lastName;
		
	}

	
	 public boolean findByEmail(String email) {	  
	  List<User> userFound = new ArrayList<User>(); 	   
	  List<User> userList = userRepository.findAll();
	  
	  for(User user : userList) 
	  { 
		  if(user.getEmail().equalsIgnoreCase(email)) 
		  {
			  userFound.add(user); 
	      } 
	  }
	  
	  if(userFound.isEmpty())
	  {
		  return false; 
	  }
	  
	  return true; 
	  }


	public User checkUser(String username, String pswd) {
		
		List<User> allUsers = userRepository.findAll();
		List<User> userFound = new ArrayList<>();
		User currentUser = null;
		for(User user : allUsers)
		{
			if(user.getEmail().equalsIgnoreCase(username))
			{
				if(user.getPassword().equals(pswd))
				{
					currentUser = user;
				}
			}
		}
		return currentUser;
//		
//		if(userFound.size() >= 1)
//		{
//			
//		}
//		else
//		{
//			return false;
//		}		
	}


	public User findUserId(String currentUserSession) {
		List<User> allUsers = userRepository.findAll();
		User currentUser = null;
		for(User user : allUsers)
		{
			if(user.getEmail().equalsIgnoreCase(currentUserSession))
			{
				currentUser = user;
			}
		}
		return currentUser;
	}


	

}

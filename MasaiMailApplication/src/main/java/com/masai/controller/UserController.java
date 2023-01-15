package com.masai.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exceptions.EmailException;
import com.masai.exceptions.LoginException;
import com.masai.exceptions.UserException;
import com.masai.model.Email;
import com.masai.model.User;
import com.masai.service.LoginService;
import com.masai.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginService loginService;
	
	
	@PostMapping("/masaimail/register")
	public ResponseEntity<User> saveCustomer(@RequestBody User user) throws UserException {
		
		User savedUser= userService.createCustomer(user);
		
		
		return new ResponseEntity<User>(savedUser,HttpStatus.CREATED);
	}
	
	@PutMapping("/masaimail/user")
	public  ResponseEntity<User> updateCustomer(@RequestBody User user,@RequestParam(required = false) String key ) throws UserException {
		
		
		User updateduser= userService.updateCustomer(user, key);
				
		return new ResponseEntity<User>(updateduser,HttpStatus.OK);
		
		
	}
	
	@GetMapping("masaimail/mail")
	public ResponseEntity<List<Email>> getAllEmails(@RequestParam String key) throws LoginException, UserException{
		
		User user = userService.getUserByUuid(key);
		
		if(user != null) {
			if(loginService.checkUserLoginOrNot(key)) {
				
				List<Email> listOfEmails = user.getListOfEmail();
				
				
				
				return new ResponseEntity<List<Email>>(listOfEmails, HttpStatus.OK);
				
				
			}else {
				 
				throw new LoginException("Invalid key or please login first");
			}
			
		}else {
			
			throw new LoginException("Invalid key " + key);
			
		}
		
		
	}
	
	@GetMapping("masaimail/starred")
	public ResponseEntity<List<Email>> getAllStarred(@RequestParam String key) throws LoginException, UserException, EmailException{
		
		User user = userService.getUserByUuid(key);
		
		if(user != null) {
			if(loginService.checkUserLoginOrNot(key)) {
				
				List<Email> listOfEmails = user.getListOfEmail();
				
				List<Email> listOfStarredEmails = new ArrayList<>();
				
				
				for(Email eachEmail: listOfEmails) {
					
					if(eachEmail.getStarMail() == true) {
						
						listOfStarredEmails.add(eachEmail);
					}
				}
				
				if(!listOfStarredEmails.isEmpty()) {
					
					return new ResponseEntity<List<Email>>(listOfStarredEmails, HttpStatus.OK);
				}else {
					
					throw new EmailException("No stared email found");
				}
				

			}else {
				 
				throw new LoginException("Invalid key or please login first");
			}
			
		}else {
			
			throw new LoginException("Invalid key " + key);
			
		}
		
		
	}
	
	
	

}

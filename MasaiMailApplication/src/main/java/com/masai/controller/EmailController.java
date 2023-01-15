package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class EmailController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginService loginService;
	
	
	@PostMapping
	@GetMapping("masaimail/mail")
	public ResponseEntity<Email> getAllStarred(@RequestParam String key, @RequestBody Email email) throws LoginException, UserException, EmailException{
		
		User user = userService.getUserByUuid(key);
		
		if(user != null) {
			if(loginService.checkUserLoginOrNot(key)) {
				
				Email sentEmail =  userService.sendEmail(user, email);
				
				return new ResponseEntity<Email>(sentEmail, HttpStatus.OK);
				
			}else {
				 
				throw new LoginException("Invalid key or please login first");
			}
			
		}else {
			
			throw new LoginException("Invalid key " + key);
			
		}
		
		
	}
	
	
}

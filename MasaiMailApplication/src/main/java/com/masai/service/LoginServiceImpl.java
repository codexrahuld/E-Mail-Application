package com.masai.service;

import java.time.LocalDateTime;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.dao.SessionDao;
import com.masai.dao.UserDao;
import com.masai.exceptions.LoginException;
import com.masai.model.CurrentUserSession;
import com.masai.model.LoginDTO;
import com.masai.model.User;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SessionDao sDao;
	
	
	
	@Override
	public String logIntoAccount(LoginDTO dto)throws LoginException{
		
		
		User existinguser= userDao.findByEmail(dto.getEmail());
		
		if(existinguser == null) {
			
			throw new LoginException("Please Enter a valid email id");
			
			 
		}

		CurrentUserSession validCustomerSessionOpt =  sDao.findByEmail(existinguser.getEmail());
		
		if(validCustomerSessionOpt != null) {
			
			throw new LoginException("User already Logged In with this number");
			
		}
		
		if(existinguser.getPassword().equals(dto.getPassword())) {
			
			String key= getSaltString();
			
			
			
			CurrentUserSession currentUserSession = new CurrentUserSession(existinguser.getEmail(),key,LocalDateTime.now());
			
			sDao.save(currentUserSession);

			return currentUserSession.toString();
		}
		else
			
			throw new LoginException("Please Enter a valid password");
		
		
	}


	@Override
	public String logOutFromAccount(String key)throws LoginException {
		
		CurrentUserSession validCustomerSession = sDao.findByUuid(key);
		
		
		if(validCustomerSession == null) {
			throw new LoginException("User Not Logged In with this number");
			
		}
		
		
		sDao.delete(validCustomerSession);
		
		
		return "Logged Out !";
		
		
	}
	
	public static String getSaltString() {
		
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	@Override
	public Boolean checkUserLoginOrNot(String key) throws LoginException {
		
		CurrentUserSession currentUserSession = sDao.findByUuid(key);
		
		if(currentUserSession != null) {
			
			return true;
			
		}else {
			
			return false;
		}
		
		
		
	}

}

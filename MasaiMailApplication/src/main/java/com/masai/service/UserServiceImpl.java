package com.masai.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.dao.SessionDao;
import com.masai.dao.UserDao;
import com.masai.exceptions.EmailException;
import com.masai.exceptions.UserException;
import com.masai.model.CurrentUserSession;
import com.masai.model.Email;
import com.masai.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userdao;
	
	@Autowired
	SessionDao sessionDao;

	@Override
	public User createCustomer(User user) throws UserException {
		
		User existinguser= userdao.findByEmail(user.getEmail());
		
		
		
		if(existinguser != null) 
			throw new UserException("User Already Registered with Mobile number");
			
		
		
		
			return userdao.save(user);
			
			
		}


	@Override
	public User updateCustomer(User user, String key) throws UserException {
		
		CurrentUserSession loggedInUser= sessionDao.findByUuid(key);
		
		if(loggedInUser == null) {
			throw new UserException("Please provide a valid key to update a user");
		}
	
		if(user.getEmail().equals(loggedInUser.getEmail()) ) {
			
			return userdao.save(user);
			
		}
		else
			throw new UserException("Invalid user Details, please login first");
	}
	
	@Override
	public User getUserByUuid(String uuid) throws UserException {
		
		CurrentUserSession user = sessionDao.findByUuid(uuid);
		
		User user2 = userdao.findByEmail(user.getEmail());
		
		if(user2 != null) {
			
			return user2;
		
		}else {
			
			throw new UserException("User not present by this uuid " + uuid);
		}
		
	}


	@Override
	public Email sendEmail(User user, Email email) throws EmailException {
		
		user.getListOfEmail().add(email);
		user.getListOfEmail().forEach(eachEmail -> eachEmail.setUser(user));
		
		userdao.save(user);
		
		return email;
		
	}

}







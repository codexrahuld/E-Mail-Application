package com.masai.service;


import com.masai.exceptions.EmailException;
import com.masai.exceptions.UserException;
import com.masai.model.Email;
import com.masai.model.User;

public interface UserService {
	
	public User createCustomer(User user)throws UserException;
	
	public User updateCustomer(User customer,String key)throws UserException;
	
	public User getUserByUuid(String uuid) throws UserException;
	
	public Email sendEmail(User user, Email email) throws EmailException;

}

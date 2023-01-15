package com.masai.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.model.User;

public interface UserDao extends JpaRepository<User, String> {
	
	public User findByEmail(String emailId);
	
}
package com.masai.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.model.CurrentUserSession;

public interface SessionDao extends JpaRepository<CurrentUserSession, String> {
	
	public CurrentUserSession findByEmail(String emailId);
	
	public  CurrentUserSession  findByUuid(String uuid);
	
}
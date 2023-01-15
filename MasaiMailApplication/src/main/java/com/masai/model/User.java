package com.masai.model;

import java.time.LocalDate;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userid;
	
	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private String mobileNo;
	
    private String password;	
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Email> listOfEmail;
	
	
}


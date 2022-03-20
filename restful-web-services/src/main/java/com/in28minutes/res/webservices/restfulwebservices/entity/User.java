package com.in28minutes.res.webservices.restfulwebservices.entity;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class User {
	
	private int id;
	@Size(min=2, message="Name should be at least two characters length.")
	private String firstName;
	@Past
	private Date birthDate;
	
	public User() {
	}

	public User(int id, String firstName, Date birthDate) {
		this.id = id;
		this.firstName = firstName;
		this.birthDate = birthDate;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", birthDate=" + birthDate + "]";
	}

}

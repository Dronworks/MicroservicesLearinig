package com.in28minutes.res.webservices.restfulwebservices.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in28minutes.res.webservices.restfulwebservices.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	

}

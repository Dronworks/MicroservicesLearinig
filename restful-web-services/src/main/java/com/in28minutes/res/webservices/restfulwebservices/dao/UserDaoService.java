package com.in28minutes.res.webservices.restfulwebservices.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.in28minutes.res.webservices.restfulwebservices.entity.User;

@Component
public class UserDaoService {

	private static int counter = 0;

	private static List<User> users;

	@PostConstruct
	private void initUsers() {
		users = new ArrayList<User>();
		users.add(new User(counter++, "andrey", new Date()));
		users.add(new User(counter++, "shai", new Date()));
		users.add(new User(counter++, "tal", new Date()));
	}

	public List<User> findAll(){
		return users;
	}

	public User save(User user) {
		if(user.getId() == 0) {
			user.setId(counter++);
		}
		users.add(user);
		return user;
	}

	public User findOne(int id) {
		for(User user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public User deleteById(int id) {
		Iterator<User> usersIterator = users.iterator();
		while(usersIterator.hasNext()) {
			User user = usersIterator.next();
			if(user.getId() == id) {
				usersIterator.remove();
				return user;
			}
		}
		return null;
	}

}

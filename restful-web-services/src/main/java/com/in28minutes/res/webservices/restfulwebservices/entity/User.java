package com.in28minutes.res.webservices.restfulwebservices.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity // H2 will create the database in memory during startup! Hibernate: create table. Can use this SQL in mysql!
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Size(min=2, message="Name should be at least two characters length.")
	private String firstName;
	@Past
	private Date birthDate;
	
	@OneToMany(mappedBy = "user")
	@Cascade({CascadeType.ALL})
	private List<Post> posts;
	
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
	
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", birthDate=" + birthDate + "]";
	}

}

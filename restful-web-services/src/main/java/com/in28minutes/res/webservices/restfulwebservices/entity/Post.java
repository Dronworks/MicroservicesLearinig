package com.in28minutes.res.webservices.restfulwebservices.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Post {
	
	@Id
	@GeneratedValue
	private int id;
	private String message;
	private Date timestamp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore // To avoid recursive retrieve from DB
	@Cascade({CascadeType.DETACH,CascadeType.LOCK,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE,CascadeType.REPLICATE,CascadeType.SAVE_UPDATE})
	private User user;
	
	public Post() {
	}

	public Post(int id, String message, User user) {
		this.id = id;
		this.message = message;
		this.timestamp = new Date();
		this.user = user;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", message=" + message + ", timestamp=" + timestamp + "]";
	}

}

package com.in28minutes.res.webservices.restfulwebservices.entity;

import java.util.Date;

public class Post {
	
	private int id;
	private String message;
	private Date timestamp;
	private int userId;
	
	public Post() {
	}

	public Post(int id, String message, int userId) {
		this.id = id;
		this.message = message;
		this.timestamp = new Date();
		this.userId = userId;
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

	public int getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", message=" + message + ", timestamp=" + timestamp + ", userId=" + userId + "]";
	}

}

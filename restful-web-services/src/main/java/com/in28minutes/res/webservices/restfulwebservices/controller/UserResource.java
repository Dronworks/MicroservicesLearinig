package com.in28minutes.res.webservices.restfulwebservices.controller;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import com.in28minutes.res.webservices.restfulwebservices.dao.PostDaoService;
import com.in28minutes.res.webservices.restfulwebservices.dao.UserDaoService;
import com.in28minutes.res.webservices.restfulwebservices.entity.Post;
import com.in28minutes.res.webservices.restfulwebservices.entity.User;
import com.in28minutes.res.webservices.restfulwebservices.exception.MalformedRequest;
import com.in28minutes.res.webservices.restfulwebservices.exception.NotFoundException;

@RestController
public class UserResource {
	
	@Autowired
	private UserDaoService userDaoService;
	
	@Autowired
	private PostDaoService postDaoService;

	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return userDaoService.findAll();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> getUserById(@PathVariable int id) { // Entity Model - hateoas (include links)
		User findOne = userDaoService.findOne(id);
		if(findOne == null) {
			throw new NotFoundException("id - " + id);
		}
		EntityModel<User> model = EntityModel.of(findOne); // Not enough for data links
		WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		model.add(linkToUsers.withRel("all-users"));
		return model;
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable int id) {
		User findOne = userDaoService.deleteById(id);
		if(findOne == null) {
			throw new NotFoundException("id - " + id);
		}
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		if(user.getFirstName() == null) {
			throw new MalformedRequest("Bad user provided - " + user.toString());
		}
		userDaoService.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/users/{id}/posts/{post_id}")
	public Post getUserPostById(@PathVariable int id, @PathVariable("post_id") int postId) {
		Post findOne = postDaoService.findOne(postId, id);
		if(findOne == null) {
			throw new NotFoundException(String.format("userId - %s, postId - %s" + id, postId));
		}
		return findOne;
	}
	
	@PostMapping("/users/{id}/posts")
	public Post createPost(@PathVariable int id, @RequestBody Post post) {
		if(post.getMessage() == null) {
			throw new MalformedRequest("Bad post - " + post.toString());
		}
		Post newPost = postDaoService.save(post, id);
		return newPost;
	}
	
	@GetMapping("/users/{id}/posts")
	public List<Post> getAllUserPosts(@PathVariable("id") int userId) {
		return postDaoService.findAll(userId);
	}
	
}

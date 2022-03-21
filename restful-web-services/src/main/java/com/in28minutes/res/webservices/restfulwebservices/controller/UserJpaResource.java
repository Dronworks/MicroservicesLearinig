package com.in28minutes.res.webservices.restfulwebservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.in28minutes.res.webservices.restfulwebservices.entity.Post;
import com.in28minutes.res.webservices.restfulwebservices.entity.User;
import com.in28minutes.res.webservices.restfulwebservices.exception.MalformedRequest;
import com.in28minutes.res.webservices.restfulwebservices.exception.NotFoundException;

@RestController
public class UserJpaResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> getUserById(@PathVariable int id) { // Entity Model - hateoas (include links)
		Optional<User> findById = userRepository.findById(id);
		if(!findById.isPresent()) {
			throw new NotFoundException("Not found id - " + id);
		}
		EntityModel<User> model = EntityModel.of(findById.get()); // Not enough for data links
		WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		model.add(linkToUsers.withRel("all-users"));
		return model;
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUserById(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		if(user.getFirstName() == null) {
			throw new MalformedRequest("Bad user provided - " + user.toString());
		}
		userRepository.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/jpa/users/{id}/posts/{post_id}")
	public Post getUserPostById(@PathVariable int id, @PathVariable("post_id") int postId) {
		Post findByUserIdAndId = postRepository.findByUserIdAndId(postId, id);
		if(findByUserIdAndId == null) {
			throw new NotFoundException(String.format("Post with id %s for user with id %s not found", postId, id));
		}
		return findByUserIdAndId;
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public Post createPost(@PathVariable int id, @RequestBody Post post) {
		if(post.getMessage() == null) {
			throw new MalformedRequest("Bad post - " + post.toString());
		}
		Optional<User> findById = userRepository.findById(id);
		if(!findById.isPresent()) {
			throw new NotFoundException("User with id not found - " + id);
		}
		User user = findById.get();
		post.setUser(user);
		postRepository.save(post);
		return post;
	}
	
	// NOT REALLY GOOD BECAUSE USER WILL HAVE DIFFERENT ID AND NEW BUT TESTING CASCADING
	@PostMapping("/jpa/users/{id}/posts/v2") // create user if not exists cascading
	public Post createPostWithNewUser(@PathVariable int id, @RequestBody Post post) {
		if(post.getMessage() == null) {
			throw new MalformedRequest("Bad post - " + post.toString());
		}
		Optional<User> findById = userRepository.findById(id);
		User user = new User(0, "Dron", new Date());
		if(findById.isPresent()) {
			user = findById.get();
		}
		post.setUser(user);
		postRepository.save(post);
		return post;
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> getAllUserPosts(@PathVariable("id") int userId) {
		Optional<User> findById = userRepository.findById(userId);
		if(!findById.isPresent()) {
			throw new NotFoundException("User with id not found - " + userId);
		}
		return findById.get().getPosts();
	}
	
}

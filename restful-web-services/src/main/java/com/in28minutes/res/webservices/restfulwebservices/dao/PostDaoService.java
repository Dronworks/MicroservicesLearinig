/*
 * package com.in28minutes.res.webservices.restfulwebservices.dao;
 * 
 * import java.util.ArrayList; import java.util.Date; import java.util.HashMap;
 * import java.util.List; import java.util.Map; import
 * java.util.stream.Collectors;
 * 
 * import javax.annotation.PostConstruct;
 * 
 * import org.springframework.stereotype.Component;
 * 
 * import com.in28minutes.res.webservices.restfulwebservices.entity.Post;
 * 
 * @Component public class PostDaoService {
 * 
 * private Map<Integer, Integer> counterByUser = new HashMap<>();
 * 
 * private static List<Post> posts;
 * 
 * @PostConstruct private void initUsers() { posts = new ArrayList<>();
 * posts.add(new Post(generateId(0), "Andrey first post", 0)); posts.add(new
 * Post(generateId(0), "Andrey second post", 0)); posts.add(new
 * Post(generateId(0), "Andrey third post", 0)); posts.add(new
 * Post(generateId(1), "Shai first post", 1)); posts.add(new Post(generateId(1),
 * "Shai second post", 1)); }
 * 
 * private int generateId(int userId) { if(!counterByUser.containsKey(userId)) {
 * counterByUser.put(userId, 0); } int id = counterByUser.get(userId);
 * counterByUser.put(userId, id+1); return id; }
 * 
 * public List<Post> findAll(int userId){ return posts.stream().filter(post ->
 * post.getUserId() == userId).collect(Collectors.toList()); }
 * 
 * public Post save(Post post, int userId) { if(post.getId() == 0) {
 * post.setId(generateId(userId)); } post.setTimestamp(new Date());
 * posts.add(post); return post; }
 * 
 * public Post findOne(int postId, int userId) { for(Post post : posts) {
 * if(post.getId() == postId && post.getUserId() == userId) { return post; } }
 * return null; }
 * 
 * }
 */
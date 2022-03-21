package com.in28minutes.res.webservices.restfulwebservices.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in28minutes.res.webservices.restfulwebservices.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	
	@Query(value="SELECT * FROM POST where id=:id and user_id=:user_id", nativeQuery = true)    
    Post findByUserIdAndId(@Param("id") int id, @Param("user_id") int userId);


}

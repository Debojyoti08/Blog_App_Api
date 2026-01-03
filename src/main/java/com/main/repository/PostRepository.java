package com.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.entity.Category;
import com.main.entity.Post;
import com.main.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	List<Post> getAllPostsByUser(User user);
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);

}

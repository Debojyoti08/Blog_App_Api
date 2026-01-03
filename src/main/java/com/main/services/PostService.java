package com.main.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.Category;
import com.main.entity.Post;
import com.main.entity.User;
import com.main.exceptions.ResourceNotFoundException;
import com.main.payloads.PostDto;
import com.main.repository.CategoryRepository;
import com.main.repository.PostRepository;
import com.main.repository.UserRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CategoryRepository catRepo;
	
	//create
	public PostDto createPost(PostDto postdto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		Category category = this.catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		
		Post post = this.mapper.map(postdto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newpost = this.repo.save(post);
		
		return this.mapper.map(newpost, PostDto.class);
	}
	
	public List<PostDto> getPostByUser(Integer userId) {
	    User user = userRepo.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

	    List<Post> posts = this.repo.findByUser(user);

	    List<PostDto> postdtos = posts.stream()
	            .map(post -> this.mapper.map(post, PostDto.class))
	            .collect(Collectors.toList());

	    return postdtos;
	}

	
	public List<PostDto> getPostByCategory(Integer categoryId) {
	    Category cat = catRepo.findById(categoryId)
	            .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

	    List<Post> posts = this.repo.findByCategory(cat);

	    List<PostDto> postdtos = posts.stream()
	            .map(post -> this.mapper.map(post, PostDto.class))
	            .collect(Collectors.toList());

	    return postdtos;
	}
	
	public List<PostDto> getAllPosts() {
		
		List<Post> posts = this.repo.findAll();
		List<PostDto> dtos = posts.stream().map((dto) -> this.mapper.map(dto, PostDto.class))
				.collect(Collectors.toList());
		return dtos;
	}
	
	public PostDto getPostById(Integer postId) {
		Post post = this.repo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		return this.mapper.map(post, PostDto.class);
	}
	
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.repo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.repo.save(post);
		return this.mapper.map(updatedPost, PostDto.class);
	}
	
	public void deletePost(Integer postId) {
		Post post = this.repo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		this.repo.delete(post);
	}

}

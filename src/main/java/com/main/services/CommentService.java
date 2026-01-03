package com.main.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.Comment;
import com.main.entity.Post;
import com.main.exceptions.ResourceNotFoundException;
import com.main.payloads.CommentDto;
import com.main.payloads.PostDto;
import com.main.repository.CommentRepo;
import com.main.repository.PostRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepo repo;
	
	@Autowired
	private PostRepository postrepo;
	
	@Autowired
	private ModelMapper mapper;
	
	public CommentDto createComment(CommentDto commentdto, Integer postId) {
		Post post = this.postrepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		
		Comment comment = this.mapper.map(commentdto, Comment.class);
		comment.setPost(post);
		Comment savedcomment = this.repo.save(comment);
		
		return this.mapper.map(savedcomment, CommentDto.class);
	}
	
	public void deleteComment(Integer commentId) {
		Comment comment = this.repo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Commment", "Id", commentId));
		this.repo.delete(comment);
	}
}

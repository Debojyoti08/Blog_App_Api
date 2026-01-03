package com.main.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.main.entity.Category;
import com.main.entity.Comment;
import com.main.entity.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	
	private String title;
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;

	private UserDTO user;
	
	private Comment comments;

}

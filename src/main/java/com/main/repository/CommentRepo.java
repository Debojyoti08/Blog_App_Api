package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}

package com.example.eblog.service;

import com.example.eblog.dto.CommentDTO;
import com.example.eblog.model.UserDetailsImpl;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<?> getCommentsByPostId(Long postId);

    ResponseEntity<?> getCommentById(Long postId, Long commentId);

    ResponseEntity<?> addComment(Long postId, CommentDTO comment, UserDetailsImpl currentUser);
}

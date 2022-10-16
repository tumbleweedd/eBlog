package com.example.eblog.service.impl;

import com.example.eblog.dto.CommentDTO;
import com.example.eblog.model.Comment;
import com.example.eblog.model.UserDetailsImpl;
import com.example.eblog.repository.CommentRepository;
import com.example.eblog.repository.PostRepository;
import com.example.eblog.repository.UserRepository;
import com.example.eblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> getCommentsByPostId(Long postId) {
        if (postRepository.findById(postId).isEmpty()) {
            return new ResponseEntity<>(Map.of("status", "post not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(commentRepository.findCommentsByPostId(postId).stream()
                    .map(CommentDTO::new)
                    .collect(Collectors.toList()), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<?> getCommentById(Long postId, Long commentId) {
        if (postRepository.findById(postId).isPresent() && commentRepository.findCommentById(commentId).isPresent()) {
            Comment comment = commentRepository.findCommentsByIdIfPostExist(postId, commentId);
            return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.OK);
        } else if (postRepository.findById(postId).isPresent() && commentRepository.findCommentById(commentId).isEmpty()) {
            return new ResponseEntity<>(Map.of("status", "comment not found"), HttpStatus.NOT_FOUND);
        } else if (postRepository.findById(postId).isEmpty() && commentRepository.findCommentById(commentId).isPresent()) {
            return new ResponseEntity<>(Map.of("status", "post not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> addComment(Long postId, CommentDTO comment, UserDetailsImpl currentUser) {
        if (postRepository.findById(postId).isPresent()) {
            Comment newComment = Comment.builder()
                    .body(comment.getBody())
                    .dateCreation(new Date())
                    .post(postRepository.findById(postId).get())
                    .user(userRepository.findUserByUsername(currentUser.getUsername()))
                    .build();
            commentRepository.save(newComment);
            return new ResponseEntity<>(new CommentDTO(newComment), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("post not found", HttpStatus.NOT_FOUND);
        }
    }
}

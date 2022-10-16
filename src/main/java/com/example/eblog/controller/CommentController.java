package com.example.eblog.controller;

import com.example.eblog.dto.CommentDTO;
import com.example.eblog.model.UserDetailsImpl;
import com.example.eblog.repository.CommentRepository;
import com.example.eblog.service.CommentService;
import com.example.eblog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final PostService postService;

    public CommentController(CommentRepository commentRepository, CommentService commentService, PostService postService) {
        this.commentRepository = commentRepository;
        this.commentService = commentService;
        this.postService = postService;
    }

    @GetMapping("")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentByCommentId(@PathVariable(name = "id") Long commentId,
                                                   @PathVariable Long postId) {
        return commentService.getCommentById(postId, commentId);
    }

    @PostMapping()
    public ResponseEntity<?> createComment(@PathVariable Long postId,
                                           @RequestBody CommentDTO comment,
                                           @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return commentService.addComment(postId, comment, currentUser);
    }


}

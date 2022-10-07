package com.example.eblog.controller;

import com.example.eblog.dto.PostDTO;
import com.example.eblog.model.Post;
import com.example.eblog.model.UserDetailsImpl;
import com.example.eblog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<?> newPost(@AuthenticationPrincipal UserDetailsImpl currentUser, @RequestBody PostDTO post) {
        return postService.addNewPost(currentUser, post);
    }

    @GetMapping()
    public ResponseEntity<?> getAllPosts() {
        return postService.getPostList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable Long id) {
        return postService.deletePostById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id) {
        return postService.updatePost(id);
    }
}

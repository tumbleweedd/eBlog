package com.example.eblog.controller;

import com.example.eblog.dto.PostDTO;
import com.example.eblog.model.Post;
import com.example.eblog.model.UserDetailsImpl;
import com.example.eblog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

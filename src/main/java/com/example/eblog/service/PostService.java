package com.example.eblog.service;

import com.example.eblog.dto.PostDTO;
import com.example.eblog.model.Post;
import com.example.eblog.model.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;

import java.security.Principal;

public interface PostService {
    ResponseEntity<?> addNewPost(UserDetailsImpl currentUser, PostDTO post);

    ResponseEntity<?> getPostList();

    ResponseEntity<?> getPostById(Long id);

    ResponseEntity<?> deletePostById(Long id);

    ResponseEntity<?> updatePost(Long id);
}

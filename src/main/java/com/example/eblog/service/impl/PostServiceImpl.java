package com.example.eblog.service.impl;

import com.example.eblog.dto.PostDTO;
import com.example.eblog.model.*;
import com.example.eblog.repository.CategoryRepository;
import com.example.eblog.repository.PostRepository;
import com.example.eblog.repository.TagRepository;
import com.example.eblog.repository.UserRepository;
import com.example.eblog.service.PostService;
import com.example.eblog.util.EmptyJsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    private final TagRepository tagRepository;

    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public ResponseEntity<?> addNewPost(UserDetailsImpl currentUser, PostDTO post) {
        User user = userRepository.findUserByUsername(currentUser.getUsername());
        Category category = categoryRepository.findByName(post.getCategory());

        List<Tag> tags = new ArrayList<>(post.getTags().size());

        for (String name : post.getTags()) {
            Tag tag = tagRepository.findByName(name);
            tag = tag == null ? tagRepository.save(new Tag(name)) : tag;

            tags.add(tag);
        }


        Post postOne = Post.builder()
                .body(post.getBody())
                .head(post.getTitle())
                .category(category)
                .user(user)
                .tags(tags)
                .build();
        postRepository.save(postOne);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getPostList() {
        List<Post> posts = postRepository.findAll();
        if (!posts.isEmpty()) {
            return new ResponseEntity<>(postRepository.findAll().stream().map(PostDTO::new).collect(Collectors.toList()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getPostById(Long id) {
        if (postRepository.findById(id).isPresent()) {
            Optional<Post> post = postRepository.findById(id);
            return new ResponseEntity<>(new PostDTO(post.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> deletePostById(Long id) {
        if (postRepository.findById(id).isPresent()) {
            postRepository.delete(postRepository.findById(id).get());
            return new ResponseEntity<>("Delete success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> updatePost(Long id) {
        return null;
    }
}

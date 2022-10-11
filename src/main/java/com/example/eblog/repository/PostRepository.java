package com.example.eblog.repository;

import com.example.eblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select p from Post p where p.user.id = ?1")
    List<Post> findAllPostsByUserId(Long userId);
}

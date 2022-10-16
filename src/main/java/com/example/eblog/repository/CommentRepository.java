package com.example.eblog.repository;

import com.example.eblog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select c from Comment c where c.post.id=?1")
    List<Comment> findCommentsByPostId(Long postId);

    @Query(value = "select c from Comment c where c.post.id=?1 and c.id=?2")
    Comment findCommentsByIdIfPostExist(Long postId, Long commentId);

    @Query(value = "select c from Comment c where c.id=?1")
    Optional <Comment> findCommentById(Long id);
}

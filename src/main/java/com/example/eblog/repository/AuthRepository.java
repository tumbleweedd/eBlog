package com.example.eblog.repository;

import com.example.eblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    @Query(value = "select u from User u where u.email = ?1")
    Optional<User> findUserByEmail(String email);

    User findByUsername(String username);

    @Query(value = "select u from User u where u.username = ?1")
    Optional<User> findUserByUsername(String username);
}

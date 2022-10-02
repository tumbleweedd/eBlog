package com.example.eblog.repository;

import com.example.eblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String username);
    User findUserByUsername(String username);
    @Query(value = "select u from User u where u.email = ?1")
    Optional<User> findUserByEmailInContainer(String username);
    @Query(value = "select u from User u where u.username = ?1")
    Optional<User> findByUsernameInContainer(String username);
}

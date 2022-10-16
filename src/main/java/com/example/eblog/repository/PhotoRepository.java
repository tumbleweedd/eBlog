package com.example.eblog.repository;

import com.example.eblog.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}

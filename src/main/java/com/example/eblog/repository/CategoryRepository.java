package com.example.eblog.repository;

import com.example.eblog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select c from Category c where c.id=?1")
    Optional<Category> findById(Long id);

    @Query(value = "select c from Category c where c.name=?1")
    Category findByName(String name);
}

package com.example.eblog.repository;

import com.example.eblog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query(value = "select t from Tag t where t.name=?1")
    Tag findByName(String name);
}

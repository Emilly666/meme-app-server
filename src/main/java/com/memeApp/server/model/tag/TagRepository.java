package com.memeApp.server.model.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query(value = "SELECT * FROM tags", nativeQuery = true)
    List<Tag> getAll();
    Optional<Tag> findByName(String name);
    Tag findTagByName(String name);
}

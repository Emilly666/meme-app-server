package com.memeApp.server.model.meme;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemeRepository extends JpaRepository<Meme, Integer> {
    Optional<Meme> findById(Integer id);
}

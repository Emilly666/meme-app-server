package com.memeApp.server.model.meme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemeRepository extends JpaRepository<Meme, Integer> {
    Optional<Meme> findById(Integer id);

    @Query(value = "SELECT coalesce(max(id), 0) FROM memes", nativeQuery = true)
    public Integer getMaxId();

    @Query(value = "SELECT * FROM memes WHERE id < :lastMeme_id ORDER BY id DESC LIMIT :memesCount", nativeQuery = true)
    public List<Meme> getNextMemes(Integer lastMeme_id, Integer memesCount);
}

package com.memeApp.server.model.meme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemeRepository extends JpaRepository<Meme, Integer> {
    Optional<Meme> findById(Integer id);

    @Query(value = "SELECT coalesce(max(id)+1, 0) FROM memes", nativeQuery = true)
    public Integer getMaxId();

    @Query(value = "SELECT * " +
            "FROM memes " +
            "WHERE id < :lastMeme_id ORDER BY id DESC LIMIT :memesCount", nativeQuery = true)
    public List<Meme> getNextMemes(Integer lastMeme_id, Integer memesCount);

    @Query(value = "SELECT m.id, m.add_timestamp, m.file_path, m.title, m.total_likes, m.user_id  " +
            "FROM memes m " +
            "JOIN meme_tags s ON m.id=s.meme_id " +
            "JOIN tags t ON t.id=s.tag_id " +
            "WHERE id < :lastMeme_id AND t.id=:tag_id ORDER BY id DESC LIMIT :memesCount", nativeQuery = true)
    public List<Meme> getNextMemes(Integer lastMeme_id, Integer memesCount, Integer tag_id);
}

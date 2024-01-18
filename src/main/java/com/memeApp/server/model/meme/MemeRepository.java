package com.memeApp.server.model.meme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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

    @Query(value = "SELECT m.id, m.add_timestamp, m.file_path, m.content_type, m.title, m.total_likes, m.user_id  " +
            "FROM memes m " +
            "JOIN meme_tags s ON m.id=s.meme_id " +
            "JOIN tags t ON t.id=s.tag_id " +
            "WHERE m.id < :lastMeme_id AND t.id=:tag_id ORDER BY m.id DESC LIMIT :memesCount", nativeQuery = true)
    public List<Meme> getNextMemes(Integer lastMeme_id, Integer memesCount, Integer tag_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE memes SET total_likes=total_likes+1 WHERE id=:meme_id", nativeQuery = true)
    public void addLike(Integer meme_id);
    @Transactional
    @Modifying
    @Query(value = "UPDATE memes SET total_likes=total_likes-1 WHERE id=:meme_id", nativeQuery = true)
    public void removeLike(Integer meme_id);

    @Query(value = "SELECT count(id) FROM memes WHERE user_id=:user_id", nativeQuery = true)
    public int getTotalMemesUploaded(Integer user_id);
}

package com.memeApp.server.model.meme.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface MemeLikesRepository extends JpaRepository<MemeLikes, Integer> {

    @Query(value = "SELECT value FROM meme_likes WHERE user_id=:user_id AND meme_id=:meme_id", nativeQuery = true)
    public Integer getValue(Integer user_id, Integer meme_id);
    @Query(value = "SELECT * FROM meme_likes WHERE user_id=:user_id AND meme_id=:meme_id", nativeQuery = true)
    public Integer getAll(Integer user_id, Integer meme_id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM meme_likes WHERE meme_id=:meme_id AND user_id=:user_id", nativeQuery = true)
    public void deleteBy(Integer meme_id, Integer user_id);
}

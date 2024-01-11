package com.memeApp.server.model.meme.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MemeLikesRepository extends JpaRepository<MemeLikes, Integer> {
    @Query(value = "SELECT value FROM meme_likes WHERE user_id=:user_id AND meme_id=:meme_id", nativeQuery = true)
    public Integer getValue(Integer user_id, Integer meme_id);
}

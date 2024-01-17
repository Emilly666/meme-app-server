package com.memeApp.server.model.meme.comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemeCommentsRepository extends JpaRepository<MemeComment, Integer> {

    @Query(value = "SELECT * FROM meme_comments WHERE meme_id=:meme_id", nativeQuery = true)
    public List<MemeComment> getAll(Integer meme_id);
}

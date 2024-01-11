package com.memeApp.server.model.memeTag;


import com.memeApp.server.model.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemeTagRepository extends JpaRepository<MemeTag, Integer> {

    @Query(value = "SELECT t.id, t.name " +
            "FROM tags t " +
            "JOIN meme_tags m ON t.id=m.tag_id " +
            "WHERE m.meme_id=:meme_id ",
            nativeQuery = true)
    public List<Object[]> getMemeTagsByMemeId(Integer meme_id);
}

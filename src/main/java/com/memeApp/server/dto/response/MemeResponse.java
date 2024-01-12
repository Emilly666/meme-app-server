package com.memeApp.server.dto.response;

import com.memeApp.server.model.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemeResponse {

    private Integer id;
    private String file_path;
    private String content_type;
    private String title;
    private Timestamp add_timestamp = new Timestamp(System.currentTimeMillis());
    private int total_likes = 0;
    private Integer author_id;
    private String author_nickname;
    private int reactionValue;
    private List<Tag> tags;
}

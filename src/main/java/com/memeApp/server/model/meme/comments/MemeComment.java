package com.memeApp.server.model.meme.comments;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name = "meme_comments")
public class MemeComment {
    @Id
    @GeneratedValue()
    private Integer id;
    private Integer meme_id;
    private Integer user_id;
    @Column(columnDefinition="TEXT")
    private String comment;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp add_timestamp = new Timestamp(System.currentTimeMillis());
}

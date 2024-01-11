package com.memeApp.server.model.meme;

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
@Table(name = "memes")
public class Meme {

    @Id
    @GeneratedValue()
    private Integer id;
    private String file_path;
    private String title;
    private Integer user_id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp add_timestamp = new Timestamp(System.currentTimeMillis());
    private int total_likes = 0;
}

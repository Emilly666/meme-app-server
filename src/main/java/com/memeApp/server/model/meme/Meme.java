package com.memeApp.server.model.meme;

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
    private long user_id;
    private Timestamp add_timestamp = new Timestamp(System.currentTimeMillis());
    private int total_likes = 0;
}

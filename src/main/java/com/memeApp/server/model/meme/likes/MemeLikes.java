package com.memeApp.server.model.meme.likes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name = "meme_likes")
public class MemeLikes {
    @Id
    @GeneratedValue()
    private Integer id;
    private Integer meme_id;
    private Integer user_id;
    private Integer value;
}

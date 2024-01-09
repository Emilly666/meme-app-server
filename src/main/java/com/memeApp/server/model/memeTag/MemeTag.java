package com.memeApp.server.model.memeTag;

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
@Table(name = "memeTags")
public class MemeTag {
    @Id
    @GeneratedValue()
    private Integer id;
    private Integer meme_id;
    private Integer tag_id;
}

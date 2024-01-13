package com.memeApp.server.model.tag;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity()
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue()
    private Integer id;
    private String name;

    public Tag(Integer id, String name){
        this.id = id;
        this.name = name;
    }
}

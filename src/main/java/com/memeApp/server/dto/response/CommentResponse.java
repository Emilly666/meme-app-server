package com.memeApp.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private String authorNickname;
    private String comment;
    private Timestamp add_timestamp = new Timestamp(System.currentTimeMillis());
}

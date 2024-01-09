package com.memeApp.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMemesRequest {
    private Integer lastMeme_id;
    private Integer tag_id;
    private Integer count;
}

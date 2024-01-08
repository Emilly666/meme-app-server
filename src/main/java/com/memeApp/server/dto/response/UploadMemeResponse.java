package com.memeApp.server.dto.response;

import com.memeApp.server.model.meme.Meme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadMemeResponse {
    private Meme meme;
}

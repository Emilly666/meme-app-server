package com.memeApp.server.dto.response;

import com.memeApp.server.model.meme.Meme;
import com.memeApp.server.model.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMemeResponse {
    private List<Meme> memes;
    private List<List<Tag>> tags;

}

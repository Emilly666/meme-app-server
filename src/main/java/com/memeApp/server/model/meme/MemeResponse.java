package com.memeApp.server.model.meme;

import com.memeApp.server.model.tag.Tag;
import com.memeApp.server.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemeResponse {
    private User user;
    private Meme meme;
    private List<Tag> tags;
    private Integer liked;
}

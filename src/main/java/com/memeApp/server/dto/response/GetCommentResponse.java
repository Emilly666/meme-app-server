package com.memeApp.server.dto.response;

import com.memeApp.server.model.meme.comments.MemeComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentResponse {
    private List<MemeComment> commentsList;
}

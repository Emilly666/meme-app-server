package com.memeApp.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private String nickname;
    private int totalMemesUploaded;
    private int totalLikesGiven;
    private int totalDislikesGiven;
    private int totalLikesReceived;
    private int totalDislikesReceived;
    private int totalComments;
}

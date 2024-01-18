package com.memeApp.server.service;

import com.memeApp.server.dto.response.UserProfileResponse;
import com.memeApp.server.model.meme.MemeRepository;
import com.memeApp.server.model.meme.comments.MemeCommentsRepository;
import com.memeApp.server.model.meme.likes.MemeLikesRepository;
import com.memeApp.server.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserRepository userRepository;
    private final MemeLikesRepository memeLikesRepository;
    private final MemeCommentsRepository memeCommentsRepository;
    private final MemeRepository memeRepository;

    public UserProfileResponse getUserProfile(Integer user_id){
        var user = userRepository.findUserById(user_id);
        if(user == null){
            return null;
        }
        return UserProfileResponse.builder()
                .nickname(user.getNickname())
                .totalMemesUploaded(memeRepository.getTotalMemesUploaded(user_id))
                .totalLikesGiven(memeLikesRepository.getTotalLikesGiven(user_id))
                .totalDislikesGiven(memeLikesRepository.getTotalDislikesGiven(user_id))
                .totalLikesReceived(memeLikesRepository.getTotalLikesReceived(user_id))
                .totalLikesReceived(memeLikesRepository.getTotalDislikesReceived(user_id))
                .totalComments(memeCommentsRepository.getCommentCountByUserId(user_id))
                .build();
    }
}

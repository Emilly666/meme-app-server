package com.memeApp.server.service;

import com.memeApp.server.config.JwtService;
import com.memeApp.server.model.meme.MemeRepository;
import com.memeApp.server.model.meme.likes.MemeLikes;
import com.memeApp.server.model.meme.likes.MemeLikesRepository;
import com.memeApp.server.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final MemeLikesRepository memeLikesRepository;
    private final MemeRepository memeRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String like(Integer meme_id, String token){

        var user = userRepository.findByEmail(jwtService.extractUsername(token)).orElseThrow();

        if(!jwtService.isTokenValid(token, user)){
            return null;
        }
        Integer value = memeLikesRepository.getValue(user.getId(), meme_id);
        if(value == null){
            memeRepository.addLike(meme_id);
            memeLikesRepository.save(MemeLikes.builder()
                            .meme_id(meme_id)
                            .user_id(user.getId())
                            .value(1)
                            .build());
            return "Like added";
        }else if(value == -1){
            memeRepository.addLike(meme_id);
            memeRepository.addLike(meme_id);
            memeLikesRepository.deleteBy(meme_id, user.getId());
            memeLikesRepository.save(MemeLikes.builder()
                    .meme_id(meme_id)
                    .user_id(user.getId())
                    .value(1)
                    .build());
            return "Dislike changed to like";
        }else{
            memeRepository.removeLike(meme_id);
            memeLikesRepository.deleteBy(meme_id, user.getId());
            return "Like removed";
        }
    }

    public String dislike(Integer meme_id, String token){

        var user = userRepository.findByEmail(jwtService.extractUsername(token)).orElseThrow();

        if(!jwtService.isTokenValid(token, user)){
            return null;
        }
        Integer value = memeLikesRepository.getValue(user.getId(), meme_id);
        if(value == null){
            memeRepository.removeLike(meme_id);
            memeLikesRepository.save(MemeLikes.builder()
                    .meme_id(meme_id)
                    .user_id(user.getId())
                    .value(-1)
                    .build());
            return "Dislike added";
        }else if(value == 1){
            memeRepository.removeLike(meme_id);
            memeRepository.removeLike(meme_id);
            memeLikesRepository.deleteBy(meme_id, user.getId());
            memeLikesRepository.save(MemeLikes.builder()
                    .meme_id(meme_id)
                    .user_id(user.getId())
                    .value(-1)
                    .build());
            return "Like changed to dislike";
        }else{
            memeRepository.addLike(meme_id);
            memeLikesRepository.deleteBy(meme_id, user.getId());
            return "Dislike removed";
        }
    }
}

package com.memeApp.server.service;

import com.memeApp.server.config.JwtService;
import com.memeApp.server.dto.request.AddCommentRequest;
import com.memeApp.server.model.meme.comments.MemeComment;
import com.memeApp.server.model.meme.comments.MemeCommentsRepository;
import com.memeApp.server.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemeCommentsRepository memeCommentsRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String addComment(AddCommentRequest request, String token){

        var user = userRepository.findByEmail(jwtService.extractUsername(token)).orElseThrow();

        if(!jwtService.isTokenValid(token, user)){
            return null;
        }
        MemeComment memeComment = new MemeComment();
        memeComment.setMeme_id(request.getMeme_id());
        memeComment.setUser_id(user.getId());
        memeComment.setComment(request.getComment());
        
        memeCommentsRepository.save(memeComment);
        return "Comment added successfully";
    }

    public List<MemeComment> getComments(Integer meme_id){
        return memeCommentsRepository.getAll(meme_id);
    }
}

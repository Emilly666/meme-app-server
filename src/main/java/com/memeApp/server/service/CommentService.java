package com.memeApp.server.service;

import com.memeApp.server.config.JwtService;
import com.memeApp.server.dto.request.AddCommentRequest;
import com.memeApp.server.dto.response.CommentResponse;
import com.memeApp.server.dto.response.GetCommentResponse;
import com.memeApp.server.dto.response.GetMemesResponse;
import com.memeApp.server.model.meme.comments.MemeComment;
import com.memeApp.server.model.meme.comments.MemeCommentsRepository;
import com.memeApp.server.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
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

    public GetCommentResponse getComments(Integer meme_id){
        List<MemeComment> commentList = memeCommentsRepository.getAll(meme_id);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for(int i = 0; i < commentList.size();i++){
            var user = userRepository.findUserById(commentList.get(i).getUser_id());
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setAuthorNickname(user.getNickname());
            commentResponse.setComment(commentList.get(i).getComment());
            commentResponse.setAdd_timestamp(commentList.get(i).getAdd_timestamp());

            commentResponseList.add(commentResponse);
        }
        GetCommentResponse getCommentResponse = new  GetCommentResponse();
        getCommentResponse.setCommentsList(commentResponseList);
        return getCommentResponse;
    }
}

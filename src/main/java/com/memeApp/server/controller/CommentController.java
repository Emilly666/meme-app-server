package com.memeApp.server.controller;

import com.memeApp.server.dto.request.AddCommentRequest;
import com.memeApp.server.dto.request.GetMemesRequest;
import com.memeApp.server.dto.request.LikeRequest;
import com.memeApp.server.model.meme.comments.MemeCommentsRepository;
import com.memeApp.server.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/get")
    public ResponseEntity<?> get(@RequestBody LikeRequest commentRequest){
        return ResponseEntity.ok(commentService.getComments(commentRequest.getMeme_id()));
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AddCommentRequest request, @RequestHeader("Authorization")String token){
        return ResponseEntity.ok(commentService.addComment(request, token.substring(7)));
    }
}

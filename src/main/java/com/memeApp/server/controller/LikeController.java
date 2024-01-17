package com.memeApp.server.controller;

import com.memeApp.server.dto.request.LikeRequest;
import com.memeApp.server.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<String> Like(
            @RequestBody LikeRequest likeRequest,
            @RequestHeader("Authorization")String token
    ){
        return ResponseEntity.ok(Objects
                .requireNonNullElse(
                        likeService.like(likeRequest.getMeme_id(), token.substring(7)),
                        "Like error"
                ));
    }
    @PostMapping("/dislike")
    public ResponseEntity<String> Dislike(
            @RequestBody LikeRequest likeRequest,
            @RequestHeader("Authorization")String token
    ){
        return ResponseEntity.ok(Objects
                .requireNonNullElse(
                        likeService.dislike(likeRequest.getMeme_id(), token.substring(7)),
                        "Dislike error"
                ));
    }
}

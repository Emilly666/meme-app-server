package com.memeApp.server.controller;

import com.memeApp.server.dto.request.UploadMemeRequest;
import com.memeApp.server.dto.response.UploadMemeResponse;
import com.memeApp.server.model.meme.Meme;
import com.memeApp.server.service.MemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/meme")
@RequiredArgsConstructor
public class MemeController {
    private final MemeService memeService;

    @PostMapping("/upload")
    public ResponseEntity<UploadMemeResponse> upload(
            @RequestParam("image")MultipartFile file,
            @RequestParam("title")String title,
            @RequestParam("user_id")Integer user_id,
            @RequestHeader("Authorization")String token
    ) throws IOException {
        return ResponseEntity.ok(memeService.upload(new UploadMemeRequest(title, user_id, file), token.substring(7)));
    }
}

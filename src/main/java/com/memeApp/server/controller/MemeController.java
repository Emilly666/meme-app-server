package com.memeApp.server.controller;

import com.memeApp.server.dto.request.UploadMemeRequest;
import com.memeApp.server.dto.response.UploadMemeResponse;
import com.memeApp.server.model.meme.Meme;
import com.memeApp.server.service.MemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    @GetMapping(value = "/png/{filePath}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> downloadPNG(
            @PathVariable String filePath
    ) throws IOException {
        ByteArrayResource inputStream = memeService.downloadMeme(filePath);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);
    }
    @GetMapping(value = "/gif/{filePath}", produces = MediaType.IMAGE_GIF_VALUE)
    public ResponseEntity<Resource> downloadGIF(
            @PathVariable String filePath
    ) throws IOException {
        ByteArrayResource inputStream = memeService.downloadMeme(filePath);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);
    }
}

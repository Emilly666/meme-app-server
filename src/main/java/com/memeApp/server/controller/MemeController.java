package com.memeApp.server.controller;

import com.memeApp.server.dto.request.GetMemesRequest;
import com.memeApp.server.dto.request.UploadMemeRequest;
import com.memeApp.server.dto.response.UploadMemeResponse;
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
import java.util.Objects;

@RestController
@RequestMapping("/meme")
@RequiredArgsConstructor
public class MemeController {
    private final MemeService memeService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam("image")MultipartFile file,
            @RequestParam("title")String title,
            @RequestHeader("Authorization")String token
    ) throws IOException {
        return ResponseEntity.ok(Objects
                .requireNonNullElse(
                        memeService.upload(new UploadMemeRequest(title, file), token.substring(7)),
                "Meme rejected"
                ));
    }
    @PostMapping("/get/{id}")
    public ResponseEntity<?> get(@RequestBody GetMemesRequest getMemesRequest, @PathVariable Integer id){
        return ResponseEntity.ok(memeService.getMemes(getMemesRequest, id));
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

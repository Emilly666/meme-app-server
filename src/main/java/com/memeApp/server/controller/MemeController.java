package com.memeApp.server.controller;

import com.memeApp.server.dto.request.GetMemesRequest;
import com.memeApp.server.dto.request.UploadMemeRequest;
import com.memeApp.server.service.MemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
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
            @RequestParam("tags")String tags,
            @RequestHeader("Authorization")String token
    ){
        try{
            return ResponseEntity.ok(Objects
                    .requireNonNullElse(
                            memeService.upload(new UploadMemeRequest(title, file, tags), token.substring(7)),
                            "Meme rejected"
                    ));
        }catch (Exception e){
            return null;
        }

    }
    @PostMapping("/get")
    public ResponseEntity<?> get(@RequestBody GetMemesRequest getMemesRequest){
        return ResponseEntity.ok(memeService.getMemes(getMemesRequest));
    }
    @GetMapping(value = "/png/{filePath}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> downloadPNG(@PathVariable String filePath) {
        try {
            ByteArrayResource inputStream = memeService.downloadMeme(filePath);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentLength(inputStream.contentLength())
                    .body(inputStream);
        }catch(IOException e) {
            return null;
        }
    }
    @GetMapping(value = "/gif/{filePath}", produces = MediaType.IMAGE_GIF_VALUE)
    public ResponseEntity<?> downloadGIF( @PathVariable String filePath ) {
        try {
            ByteArrayResource inputStream = memeService.downloadMeme(filePath);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentLength(inputStream.contentLength())
                    .body(inputStream);
        } catch (IOException e) {
            return null;
        }
    }
}

package com.memeApp.server.controller;

import com.memeApp.server.dto.request.UploadMemeRequest;
import com.memeApp.server.dto.response.DownloadMemeResponse;
import com.memeApp.server.service.MemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final MemeService memeService;
    @GetMapping
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("jest git");
    }


}

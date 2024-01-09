package com.memeApp.server.controller;

import com.memeApp.server.service.MemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

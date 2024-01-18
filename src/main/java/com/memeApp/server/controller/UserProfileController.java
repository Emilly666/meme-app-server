package com.memeApp.server.controller;

import com.memeApp.server.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    @GetMapping(value = "/{user_id}")
    public ResponseEntity<?> getUsrProfile(@PathVariable int user_id) {
        return ResponseEntity.ok(Objects
                .requireNonNullElse(
                        userProfileService.getUserProfile(user_id),
                        "User not found"
                ));
    }
}

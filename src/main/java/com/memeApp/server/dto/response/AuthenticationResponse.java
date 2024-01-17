package com.memeApp.server.dto.response;

import com.memeApp.server.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private Integer user_id;
    private String nickname;
    private String email;
    private String password;
}

package com.meeting.ai_summarizer.controller;

import com.meeting.ai_summarizer.dto.AuthRequest;
import com.meeting.ai_summarizer.dto.AuthResponse;
import com.meeting.ai_summarizer.dto.SignUpRequest;
import com.meeting.ai_summarizer.entity.User;
import com.meeting.ai_summarizer.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpRequest authRequest){

        return  ResponseEntity.ok(authService.registerUser(authRequest));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(authService.loginUser(authRequest));
    }
}

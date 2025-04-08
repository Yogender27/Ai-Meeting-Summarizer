package com.meeting.ai_summarizer.controller;

import com.meeting.ai_summarizer.service.ZoomAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zoom")
@RequiredArgsConstructor
@Slf4j
public class ZoomController {
    private final ZoomAuthService zoomAuthService;

    @GetMapping("/oauth/callback")
    public ResponseEntity<String> zoomOAuthCallback(@RequestParam("code") String code) {
        String accessToken = zoomAuthService.exchangeCodeForAccessToken(code);
        return ResponseEntity.ok("Zoom OAuth Success! Access Token: " + accessToken);
    }
}

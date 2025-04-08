package com.meeting.ai_summarizer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class ZoomAuthService {

    private final RestTemplate restTemplate;
    @Autowired // Spring will inject the RestTemplate bean here
    public ZoomAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String exchangeCodeForAccessToken(String code) {
        String url = "https://zoom.us/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("aURwoHnJRtC9QOoIdnsBtA", "OFF5fThVLcn5AFO7P2xwwIqkcqrgw1S8");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", "http://localhost:8080/zoom/oauth/callback");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        return response.getBody().get("access_token").toString();
    }
}


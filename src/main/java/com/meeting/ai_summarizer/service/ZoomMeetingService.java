package com.meeting.ai_summarizer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class ZoomMeetingService {

    private final RestTemplate restTemplate;
    @Autowired // Spring will inject the RestTemplate bean here
    public ZoomMeetingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createMeeting(String accessToken) {
        String url = "https://api.zoom.us/v2/users/me/meetings";

        Map<String, Object> body = Map.of(
                "topic", "AI-Powered Meeting",
                "type", 2,
                "start_time", "2024-04-02T10:00:00Z",
                "duration", 60,
                "settings", Map.of("auto_recording", "cloud")
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return response.getBody();
    }

    public String getMeetingRecordings(String accessToken, String meetingId) {
        String url = "https://api.zoom.us/v2/meetings/" + meetingId + "/recordings";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        return response.getBody();  // Returns JSON with recording download links
    }
}


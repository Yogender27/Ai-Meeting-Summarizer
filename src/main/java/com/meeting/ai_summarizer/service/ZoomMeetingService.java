package com.meeting.ai_summarizer.service;

import com.meeting.ai_summarizer.dto.CreateMeetingDto;
import com.meeting.ai_summarizer.dto.MeetingListResponseDto;
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

    @Autowired
    public ZoomMeetingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createMeeting(String accessToken, CreateMeetingDto meeting) {
        String url = "https://api.zoom.us/v2/users/me/meetings";

        Map<String, Object> body = Map.of(
                "topic", meeting.getTopic(),
                "type", 2,
                "start_time", meeting.getStartDateTime(),
                "duration", meeting.getDurationMinutes(),
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


    public MeetingListResponseDto getMeetingList(String accessToken, String userId){
        String url= "https://api.zoom.us/v2/users/"+userId+"/meetings";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<MeetingListResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, request, MeetingListResponseDto.class);

        return response.getBody();

    }
}


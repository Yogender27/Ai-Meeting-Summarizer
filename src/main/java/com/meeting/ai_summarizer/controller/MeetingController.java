package com.meeting.ai_summarizer.controller;

import com.meeting.ai_summarizer.dto.CreateMeetingDto;
import com.meeting.ai_summarizer.dto.MeetingDetailsDTO;
import com.meeting.ai_summarizer.dto.MeetingListResponseDto;
import com.meeting.ai_summarizer.repository.TranscriptionRepo;
import com.meeting.ai_summarizer.service.ZoomAuthService;
import com.meeting.ai_summarizer.service.ZoomMeetingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meeting")
@RequiredArgsConstructor
@Slf4j
public class MeetingController {

    private final TranscriptionRepo transcriptionRepository;

    private final ZoomAuthService zoomAuthService;
    private final ZoomMeetingService zoomMeetingService;

    @GetMapping("/{meetingId}")
    public ResponseEntity<?> getMeetingSummary(@PathVariable String meetingId) {
        return transcriptionRepository.findByMeetingId(meetingId)
                .map(t -> ResponseEntity.ok(
                        new MeetingDetailsDTO(
                                t.getMeetingId(),
                                t.getAudioFileUrl(),
                                t.getTranscriptionText(),
                                t.getSummary()
                        )
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<String>createMeeting(@RequestBody CreateMeetingDto meetingDto,@RequestParam String code){
        if (code.isEmpty()){
            throw new RuntimeException("No Zoom Authentication Code Received");
        }
        String acessToken = zoomAuthService.exchangeCodeForAccessToken(code);
        log.info("Access Token received for zoom Auth ",acessToken);

        zoomMeetingService.createMeeting(acessToken,meetingDto);

        return ResponseEntity.ok("Meeting Created successfully");
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?>getList(@RequestParam String code,@PathVariable String userId){

        if (code.isEmpty()){
            throw new RuntimeException("No Zoom Authentication Code Received");
        }
        String acessToken = zoomAuthService.exchangeCodeForAccessToken(code);
        log.info("Access Token received for zoom Auth ",acessToken);


         MeetingListResponseDto meetingLists = zoomMeetingService.getMeetingList(acessToken,userId);


        return ResponseEntity.ok(meetingLists);
    }
}
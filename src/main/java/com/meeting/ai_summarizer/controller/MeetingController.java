package com.meeting.ai_summarizer.controller;

import com.meeting.ai_summarizer.dto.MeetingDetailsDTO;
import com.meeting.ai_summarizer.entity.Transcription;
import com.meeting.ai_summarizer.repository.TranscriptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meeting")
@RequiredArgsConstructor
public class MeetingController {

    private final TranscriptionRepo transcriptionRepository;

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
}
package com.meeting.ai_summarizer.controller;

import com.meeting.ai_summarizer.service.WhisperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/transcription")
public class TranscriptionController {

    private final WhisperService whisperService;

    public TranscriptionController(WhisperService whisperService) {
        this.whisperService = whisperService;
    }

    @GetMapping("/process")
    public ResponseEntity<String> transcribe(@RequestParam String audioFileUrl, @RequestParam String meetingId) {
        log.info("Started+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.info("📥 Received audio file URL: {}", audioFileUrl);

        String transcriptionText = whisperService.transcribeAudio(audioFileUrl, meetingId);
        if (transcriptionText != null) {
            return ResponseEntity.ok("Transcription successful! 🎤 Text: " + transcriptionText);
        } else {
            return ResponseEntity.status(500).body("Transcription failed.");
        }
    }
}

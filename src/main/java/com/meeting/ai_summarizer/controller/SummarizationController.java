package com.meeting.ai_summarizer.controller;

import com.meeting.ai_summarizer.service.SummarizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/summarization")
@RequiredArgsConstructor
public class SummarizationController {

    private final SummarizationService summarizationService;

    @PostMapping("/process")
    public ResponseEntity<String> summarize(@RequestParam String meetingId) {
        String summary = summarizationService.summarizeTranscription(meetingId);
        return ResponseEntity.ok(summary);
    }
}


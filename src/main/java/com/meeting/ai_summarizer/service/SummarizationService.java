package com.meeting.ai_summarizer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meeting.ai_summarizer.entity.Transcription;
import com.meeting.ai_summarizer.repository.TranscriptionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@Slf4j
public class SummarizationService {

    @Value("${openai.api-key}")
    private String apiKey;

    private final TranscriptionRepo transcriptionRepo;

    public SummarizationService(TranscriptionRepo transcriptionRepo) {
        this.transcriptionRepo = transcriptionRepo;
    }

    public String summarizeTranscription(String meetingId) {
        Optional<Transcription> transcription = transcriptionRepo.findByMeetingId(meetingId);

        if (transcription == null || transcription.get().getTranscriptionText() == null) {
            throw new RuntimeException("No transcription found for meetingId: " + meetingId);
        }

        String prompt = "Summarize the following meeting transcription in clear and concise bullet points:\n\n" + transcription.get();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(
                            """
                            {
                                "model": "gpt-3.5-turbo",
                                "messages": [{"role": "user", "content": "%s"}]
                            }
                            """.formatted(prompt)
                    ))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.body());
            String summary = root.path("choices").get(0).path("message").path("content").asText();

            transcription.get().setSummary(summary);
            transcriptionRepo.save(transcription.get());

            log.info("✅ Summarization completed for Meeting ID: {}", meetingId);
            return summary;

        } catch (Exception e) {
            log.error("❌ Error in summarization: {}", e.getMessage());
            return "Summarization failed.";
        }
    }
}

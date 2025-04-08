package com.meeting.ai_summarizer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meeting.ai_summarizer.entity.Transcription;
import com.meeting.ai_summarizer.repository.TranscriptionRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class WhisperService {

    @Value("${openai.api-key}")
    private String apiKey;

    private final TranscriptionRepo transcriptionRepository;

    public WhisperService(TranscriptionRepo transcriptionRepository) {
        this.transcriptionRepository = transcriptionRepository;
    }

    public String transcribeAudio(String audioFileUrl, String meetingId) {
        Path tempFilePath = null;
        try {
            log.info("🎙️ Downloading audio file from: {}", audioFileUrl);

            // ✅ Step 1: Download the file from S3 URL to a temporary file
            tempFilePath = Files.createTempFile("audio_", ".mp3");

            try (InputStream in = new URL(audioFileUrl).openStream()) {
                Files.copy(in, tempFilePath, StandardCopyOption.REPLACE_EXISTING);
            }

            log.info("✅ Audio file saved locally at: {}", tempFilePath.toAbsolutePath());

            // ✅ Step 2: Send file to Whisper API for transcription
            File audioFile = tempFilePath.toFile();
            HttpPost request = new HttpPost("https://api.openai.com/v1/audio/transcriptions");

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create()
                    .addPart("file", new FileBody(audioFile)) // Attach file
                    .addTextBody("model", "whisper-1");

            request.setEntity(entityBuilder.build());
            request.setHeader("Authorization", "Bearer " + apiKey);

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(request)) {

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(response.getEntity().getContent());
                // ✅ Log the full response for debugging
                log.info("🔍 Whisper API Response: {}", responseJson.toPrettyString());

                // ✅ Step 3: Extract Transcription Text
                String transcriptionText = responseJson.get("text").asText();

                // ✅ Step 4: Save Transcription in MySQL
                Transcription transcription = new Transcription();
                transcription.setMeetingId(meetingId);
                transcription.setTranscriptionText(transcriptionText);
                transcription.setAudioFileUrl(audioFileUrl);
                transcriptionRepository.save(transcription);

                log.info("✅ Transcription completed for Meeting ID: {}", meetingId);
                return transcriptionText;
            }

        } catch (Exception e) {
            log.error("❌ Error in Whisper Transcription: {}", e.getMessage(), e);
            return "Transcription failed.";
        } finally {
            // ✅ Step 5: Cleanup - Delete the temp file after processing
            if (tempFilePath != null) {
                try {
                    Files.deleteIfExists(tempFilePath);
                    log.info("🗑️ Temp file deleted: {}", tempFilePath);
                } catch (Exception e) {
                    log.warn("⚠️ Failed to delete temp file: {}", tempFilePath, e);
                }
            }
        }
    }
}

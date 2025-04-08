package com.meeting.ai_summarizer.controller;


import com.meeting.ai_summarizer.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
@Slf4j
public class S3Controller {


    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("📂 Upload function started for file: {}", file.getOriginalFilename());
        log.info("🟢 API Hit: /api/s3/upload");

        try {
            String fileName = s3Service.uploadFile(file);
            log.info("✅ File uploaded successfully: {}", fileName);
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (Exception e) {
            log.error("❌ Error uploading file: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Upload failed");
        }
        }

    }


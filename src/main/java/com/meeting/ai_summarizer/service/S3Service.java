package com.meeting.ai_summarizer.service;

import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class S3Service {

    private final S3Template s3Template;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Template s3Template) {
        this.s3Template = s3Template;
    }

    public String uploadFile(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            s3Template.upload(bucketName, fileName, file.getInputStream());
            return fileName;  // Return the file name as the stored identifier
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }
}

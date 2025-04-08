package com.meeting.ai_summarizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.regions.providers.DefaultAwsRegionProviderChain;

@SpringBootApplication
public class AiSummarizerApplication {
	public static void main(String[] args) {

		SpringApplication.run(AiSummarizerApplication.class, args);
	}
}

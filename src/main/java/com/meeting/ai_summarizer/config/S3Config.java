package com.meeting.ai_summarizer.config;

import io.awspring.cloud.s3.S3ObjectConverter;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3OutputStreamProvider;
import io.awspring.cloud.s3.S3Template;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {



    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    @Value("${cloud.aws.credentials.access-key}")
    private  String accessKey ;

    @Value("${cloud.aws.credentials.secret-key}")
    private  String secretKey ;

    @Value("${cloud.aws.region.static}")
    private  String region;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of("ap-south-1"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.of("ap-south-1"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    @Bean
    public S3Template s3Template(S3Client s3Client, S3Presigner s3Presigner, S3OutputStreamProvider outputStreamProvider, S3ObjectConverter s3ObjectConverter) {
        return new S3Template(s3Client, outputStreamProvider,s3ObjectConverter,s3Presigner);
    }
}

package com.meeting.ai_summarizer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transcriptions")
public class Transcription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String meetingId; // Reference to Zoom Meeting

    @Column(nullable = false, length = 5000)
    private String transcriptionText; // Transcribed Text

    @Column(nullable = false)
    private String audioFileUrl; // S3 URL of the audio file

    @Column(columnDefinition = "TEXT") // For summary
    private String summary;

}

package com.meeting.ai_summarizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingDetailsDTO {
    private String meetingId;
    private String audioFileUrl;
    private String transcription;
    private String summary;
}

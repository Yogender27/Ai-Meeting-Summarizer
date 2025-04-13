package com.meeting.ai_summarizer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMeetingDto {

    private String topic;
    private String startDateTime;
    private int durationMinutes;
}

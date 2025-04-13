package com.meeting.ai_summarizer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MeetingListResponseDto {
    private int total_records;
    private List<MeetingDetailsDTO> meetings;
}

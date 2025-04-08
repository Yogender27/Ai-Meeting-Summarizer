package com.meeting.ai_summarizer.repository;

import com.meeting.ai_summarizer.entity.Transcription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TranscriptionRepo extends JpaRepository<Transcription,Long> {
    Optional<Transcription> findByMeetingId(String meetingId);

}

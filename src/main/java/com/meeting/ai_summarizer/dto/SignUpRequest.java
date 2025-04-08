package com.meeting.ai_summarizer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {


    private String email;

    private String password;

    private String fullName;
}

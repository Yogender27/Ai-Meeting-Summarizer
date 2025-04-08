package com.meeting.ai_summarizer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // allow all endpoints
                .allowedOrigins("http://localhost:4200") // allow your React app
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // allowed methods
                .allowedHeaders("*") // allow any headers
                .allowCredentials(true); // allow cookies/auth headers if needed
    }
}

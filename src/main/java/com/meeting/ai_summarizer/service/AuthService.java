package com.meeting.ai_summarizer.service;

import com.meeting.ai_summarizer.dto.AuthRequest;
import com.meeting.ai_summarizer.dto.AuthResponse;
import com.meeting.ai_summarizer.dto.SignUpRequest;
import com.meeting.ai_summarizer.entity.User;
import com.meeting.ai_summarizer.repository.UserRepo;
import com.meeting.ai_summarizer.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public  String registerUser(SignUpRequest authRequest){

        Optional<User>existingUser = userRepo.findByEmail(authRequest.getEmail());
        if (existingUser.isPresent()){
            throw  new RuntimeException("User Already Exists");
        }
        String hashedPassword = passwordEncoder.encode(authRequest.getPassword());
        User newUser = User.builder()
                .email(authRequest.getEmail())
                .fullName(authRequest.getFullName())
                .password(hashedPassword)
                .build();
        userRepo.save(newUser);
        log.info( "Registered for username"+authRequest.getFullName());
        return "User Registered Successfully";
    }


    public AuthResponse loginUser(AuthRequest authRequest) {
        log.debug("🔍 Checking email: {}", authRequest.getEmail());

        // Find user by email
        Optional<User> userOptional = userRepo.findByEmail(authRequest.getEmail());
        if (userOptional.isEmpty()) {
            log.error("❌ User not found for email: {}", authRequest.getEmail());
            throw new RuntimeException("Invalid Credentials");
        }

        User user = userOptional.get();
        log.debug("✅ User found: {}", user.getEmail());
        log.debug("🔑 Stored password (hashed): {}", user.getPassword());

        // Validate password
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            log.error("❌ Password does not match for user: {}", user.getEmail());
            throw new RuntimeException("Invalid Credentials");
        }

        log.info("✅ Authentication successful, generating JWT...");
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}

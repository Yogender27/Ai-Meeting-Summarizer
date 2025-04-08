package com.meeting.ai_summarizer.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column
    private String password;

    @Column(nullable = false)
    private  String fullName;

    @Enumerated(EnumType.STRING)
    private Role role;


}

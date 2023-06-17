package dev.archie.stress.tester.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UUID id;
    private String login;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private Double latitude;
    private Double longitude;
    private UserType userType;
}

package dev.archie.handymanservice.landscape.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.archie.handymanservice.landscape.UserType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class HandymanDto {
    private UUID id;

    private String login;

    private String email;

    private String phoneNumber;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdatedAt;

    private Double latitude;

    private Double longitude;

    private UserType userType;

    private String firstName;

    private String lastName;

    private byte[] photo;

    @JsonIgnoreProperties("accounts")
    private List<AccountDto> accounts;

    @JsonIgnoreProperties("handyman")
    private List<SkillDto> skills;
}

package dev.archie.rancherservice.landscape.dto;

import dev.archie.rancherservice.landscape.UserType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class GardenerDto {

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

    private List<FieldDto> fields;
}

package dev.archie.rancherservice.landscape.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatingUserDto {

    private String login;

    private String email;

    private String phoneNumber;

    private Long userTypeId;

    private Double latitude;

    private Double longitude;

}

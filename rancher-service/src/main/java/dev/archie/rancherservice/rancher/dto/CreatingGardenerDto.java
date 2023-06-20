package dev.archie.rancherservice.rancher.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CreatingGardenerDto {

    private String login;

    private String email;

    private String phone;

    private String firstName;

    private String lastName;

    private Double latitude;

    private Double longitude;

}

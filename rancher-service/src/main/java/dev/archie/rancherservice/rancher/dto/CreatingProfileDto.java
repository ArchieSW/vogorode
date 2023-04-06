package dev.archie.rancherservice.rancher.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class CreatingProfileDto {

    private String login;

    private String email;

    private String phoneNumber;

    private Double latitude;

    private Double longitude;

    private Double area;

    private List<String> jobs;

}

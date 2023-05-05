package dev.archie.handymanservice.handyman.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class CreatingHandymanDto {

    private String login;

    private String email;

    private String phoneNumber;

    private Double latitude;

    private Double longitude;

    private List<String> skills;

}

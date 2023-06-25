package dev.archie.landscapeservice.handyman.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreatingHandymanDto {

    private String login;

    private String email;

    private String phoneNumber;

    private Double latitude;

    private Double longitude;

    private List<String> skills;

    private String firstName;

    private String lastName;

}

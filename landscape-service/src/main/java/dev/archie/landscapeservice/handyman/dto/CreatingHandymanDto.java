package dev.archie.landscapeservice.handyman.dto;

import dev.archie.landscapeservice.account.dto.CreatingAccountDto;
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

package dev.archie.handymanservice.handyman.dto;

import dev.archie.handymanservice.account.dto.CreatingAccountDto;
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

    private String firstName;

    private String lastName;

    private List<CreatingAccountDto> accounts;

}

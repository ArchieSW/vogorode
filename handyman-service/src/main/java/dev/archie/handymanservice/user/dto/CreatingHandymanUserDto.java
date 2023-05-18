package dev.archie.handymanservice.user.dto;

import dev.archie.handymanservice.account.dto.CreatingAccountDto;
import lombok.Data;

import java.util.List;

@Data
public class CreatingHandymanUserDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private List<CreatingAccountDto> accounts;

    private List<String> skills;
}

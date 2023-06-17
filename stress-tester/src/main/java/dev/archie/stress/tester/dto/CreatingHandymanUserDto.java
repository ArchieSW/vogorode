package dev.archie.stress.tester.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreatingHandymanUserDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private List<String> skills;
}

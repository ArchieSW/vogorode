package dev.archie.handymanservice.user.dto;

import dev.archie.handymanservice.account.dto.CreatingAccountDto;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class CreatingHandymanUserDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private List<CreatingAccountDto> accounts;

    private List<String> skills;

    public void setAccounts(List<CreatingAccountDto> accounts) {
        if (accounts == null) {
            accounts = Collections.emptyList();
        }
        this.accounts = accounts;
    }

    public void setSkills(List<String> skills) {
        if (skills == null) {
            skills = Collections.emptyList();
        }
        this.skills = skills;
    }
}

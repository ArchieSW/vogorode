package dev.archie.rancherservice.gardener.dto;

import dev.archie.rancherservice.field.dto.CreatingFieldDto;
import lombok.Data;

import java.util.List;

@Data
public class CreatingGardenerDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private List<CreatingFieldDto> fields;
}

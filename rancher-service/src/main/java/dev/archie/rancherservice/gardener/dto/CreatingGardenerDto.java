package dev.archie.rancherservice.gardener.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
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

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    public void setFields(List<CreatingFieldDto> fields) {
        this.fields = fields;
    }
}

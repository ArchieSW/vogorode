package dev.archie.rancherservice.field;

import dev.archie.rancherservice.field.dto.CreatingFieldDto;
import org.springframework.stereotype.Component;

@Component
public class FieldMapper {
    public Field mapCreatingFieldDtoToField(CreatingFieldDto fieldDto) {
        return Field.builder()
                .address(fieldDto.getAddress())
                .latitude(fieldDto.getLatitude())
                .longitude(fieldDto.getLongitude())
                .area(fieldDto.getArea())
                .build();
    }
}

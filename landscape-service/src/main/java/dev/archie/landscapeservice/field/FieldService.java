package dev.archie.landscapeservice.field;

import dev.archie.landscapeservice.field.dto.CreatingFieldDto;
import dev.archie.landscapeservice.field.exceptions.FieldDoesNotExistsException;
import dev.archie.landscapeservice.gardener.Gardener;
import dev.archie.landscapeservice.gardener.GardenerRepository;
import dev.archie.landscapeservice.gardener.GardenerService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;
    private final GardenerService gardenerService;

    public Field create(CreatingFieldDto fieldDto, UUID gardenerId) {
        Gardener gardener = gardenerService.getById(gardenerId);
        Field field = mapCreatingFieldDtoToField(fieldDto, gardener);
        return fieldRepository.save(field);
    }

    public Field getById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new FieldDoesNotExistsException(id));
    }

    public Field update(CreatingFieldDto fieldDto, Long id) {
        Field field = getById(id);
        Field updatedField = mapCreatingFieldDtoToField(fieldDto, field.getGardener());
        updatedField.setId(field.getId());
        return fieldRepository.save(updatedField);
    }

    public void delete(Long id) {
        fieldRepository.delete(getById(id));
    }

    private Field mapCreatingFieldDtoToField(CreatingFieldDto fieldDto, Gardener gardener) {
        return Field.builder()
                .latitude(fieldDto.getLatitude())
                .longitude(fieldDto.getLongitude())
                .address(fieldDto.getAddress())
                .gardener(gardener)
                .area(fieldDto.getArea())
                .build();
    }

}

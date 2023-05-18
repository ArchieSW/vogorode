package dev.archie.rancherservice.field;

import dev.archie.rancherservice.field.dto.CreatingFieldDto;
import dev.archie.rancherservice.field.exceptions.FieldDoesNotExistsException;
import dev.archie.rancherservice.gardener.Gardener;
import dev.archie.rancherservice.gardener.GardenerRepository;
import dev.archie.rancherservice.gardener.exceptions.GardenerDoesNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;
    private final GardenerRepository gardenerRepository;

    public Field create(CreatingFieldDto fieldDto, Long gardenerId) {
        Field field = fieldMapper.mapCreatingFieldDtoToField(fieldDto);
        Gardener gardener = getGardener(gardenerId);
        field.setGardener(gardener);
        return fieldRepository.save(field);
    }

    public Field getById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new FieldDoesNotExistsException(id));
    }

    public Field update(CreatingFieldDto fieldDto, Long id) {
        Field field = getById(id);
        Field updatedField = fieldMapper.mapCreatingFieldDtoToField(fieldDto);
        updatedField.setId(field.getId());
        updatedField.setGardener(field.getGardener());
        return fieldRepository.save(updatedField);
    }

    public void delete(Long id) {
        Field field = getById(id);
        fieldRepository.delete(field);
    }

    private Gardener getGardener(Long gardenerId) {
        return gardenerRepository.findById(gardenerId)
                .orElseThrow(() -> new GardenerDoesNotExistsException(gardenerId));
    }
}

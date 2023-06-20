package dev.archie.rancherservice.field;

import dev.archie.rancherservice.field.dto.CreatingFieldDto;
import dev.archie.rancherservice.field.exception.NoSuchFieldWithIdException;
import dev.archie.rancherservice.rancher.Gardener;
import dev.archie.rancherservice.rancher.GardenerRepository;
import dev.archie.rancherservice.rancher.GardenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;

    private final GardenerService gardenerService;
    private final GardenerRepository gardenerRepository;

    public Field create(CreatingFieldDto creatingFieldDto, String gardenerId) {
        Gardener gardener = gardenerService.getById(gardenerId);
        Field field = mapCreatingFieldDtoToField(creatingFieldDto);
        field.setGardener(gardener);
        field = fieldRepository.save(field);
        gardener.getFields().add(field);
        gardenerRepository.save(gardener)
        return field;
    }

    public Field getById(String id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new NoSuchFieldWithIdException(id));
    }

    public Field update(CreatingFieldDto creatingFieldDto, String id) {
        Field field = getById(id);
        Field updatedField = mapCreatingFieldDtoToField(creatingFieldDto);
        copyField(field, updatedField);
        field = fieldRepository.save(field);
        Gardener gardener = field.getGardener();
        gardener.getFields().add(field);
        gardenerRepository.save(gardener);
        return field;
    }

    public void delete(String id) {
        fieldRepository.delete(getById(id));
    }

    private static Field mapCreatingFieldDtoToField(CreatingFieldDto creatingFieldDto) {
        return Field.builder()
                .address(creatingFieldDto.getAddress())
                .area(creatingFieldDto.getArea())
                .longitude(creatingFieldDto.getLongitude())
                .latitude(creatingFieldDto.getLatitude())
                .address(creatingFieldDto.getAddress())
                .jobs(creatingFieldDto.getJobs())
                .build();
    }

    private static void copyField(Field destination, Field source) {
        destination.setJobs(source.getJobs());
        destination.setArea(source.getArea());
        destination.setAddress(source.getAddress());
        destination.setLatitude(source.getLatitude());
        destination.setLongitude(source.getLongitude());
    }

}

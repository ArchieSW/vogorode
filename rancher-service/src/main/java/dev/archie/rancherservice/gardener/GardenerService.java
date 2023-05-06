package dev.archie.rancherservice.gardener;

import dev.archie.rancherservice.field.Field;
import dev.archie.rancherservice.field.FieldService;
import dev.archie.rancherservice.gardener.dto.CreatingGardenerDto;
import dev.archie.rancherservice.gardener.exceptions.GardenerAlreadyExistsException;
import dev.archie.rancherservice.gardener.exceptions.GardenerDoesNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GardenerService {

    private final GardenerRepository gardenerRepository;
    private final FieldService fieldService;

    public Gardener create(CreatingGardenerDto gardenerDto) {
        Gardener gardener = mapCreatingGardenerDtoToGardener(gardenerDto);
        if (gardenerRepository.existsByEmail(gardener.getEmail())) {
            throw new GardenerAlreadyExistsException(gardener.getEmail());
        }
        gardenerRepository.save(gardener);
        List<Field> fields = gardenerDto.getFields().stream()
                .map(fieldDto -> fieldService.create(fieldDto, gardener.getId()))
                .toList();
        gardener.setFields(fields);
        return gardener;
    }

    public Gardener getById(Long id) {
        return gardenerRepository.findById(id)
                .orElseThrow(() -> new GardenerDoesNotExistsException(id));
    }

    public Gardener update(Long id, CreatingGardenerDto gardenerDto) {
        Gardener existingGardener = getById(id);
        Optional<Gardener> possibleGardenerByNewEmail = gardenerRepository.findByEmail(gardenerDto.getEmail());
        if (possibleGardenerByNewEmail.isPresent() && !possibleGardenerByNewEmail.get().equals(existingGardener)) {
            throw new GardenerAlreadyExistsException(gardenerDto.getEmail());
        }
        existingGardener.setEmail(gardenerDto.getEmail());
        existingGardener.setPhone(gardenerDto.getPhone());
        existingGardener.setLastName(gardenerDto.getLastName());
        existingGardener.setFirstName(gardenerDto.getFirstName());
        return gardenerRepository.save(existingGardener);
    }

    public void deleteById(Long id) {
        Gardener gardener = getById(id);
        gardenerRepository.delete(gardener);
    }

    private Gardener mapCreatingGardenerDtoToGardener(CreatingGardenerDto gardenerDto) {
        return Gardener.builder()
                .phone(gardenerDto.getPhone())
                .lastName(gardenerDto.getLastName())
                .firstName(gardenerDto.getFirstName())
                .email(gardenerDto.getEmail())
                .phone(gardenerDto.getPhone())
                .build();
    }
}

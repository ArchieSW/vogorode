package dev.archie.landscapeservice.gardener;

import dev.archie.landscapeservice.gardener.dto.CreatingGardenerDto;
import dev.archie.landscapeservice.user.exceptions.NoSuchUserException;
import dev.archie.landscapeservice.user.type.UserType;
import dev.archie.landscapeservice.user.type.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GardenerService {

    private final GardenerRepository gardenerRepository;
    private final UserTypeRepository userTypeRepository;

    public Gardener create(CreatingGardenerDto gardenerDto) {
        Gardener gardener = mapCreatingGardenerDtoToGardener(gardenerDto);
        gardener.setCreatedAt(LocalDateTime.now());
        gardener.setLastUpdatedAt(LocalDateTime.now());
        return gardenerRepository.save(gardener);
    }

    public Gardener getById(UUID id) {
        return gardenerRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(id));
    }

    public Gardener update(CreatingGardenerDto creatingGardenerDto, UUID id) {
        Gardener gardener = getById(id);
        Gardener updated = mapCreatingGardenerDtoToGardener(creatingGardenerDto);
        gardener.setEmail(updated.getEmail());
        gardener.setLogin(updated.getLogin());
        gardener.setLatitude(updated.getLatitude());
        gardener.setLongitude(updated.getLongitude());
        gardener.setPhoneNumber(updated.getPhoneNumber());
        gardener.setLastName(updated.getLastName());
        gardener.setFirstName(updated.getFirstName());
        gardener.setLastUpdatedAt(LocalDateTime.now());
        return gardenerRepository.save(gardener);
    }

    public void delete(UUID id) {
        gardenerRepository.delete(getById(id));
    }

    private Gardener mapCreatingGardenerDtoToGardener(CreatingGardenerDto gardenerDto) {
        Gardener gardener = new Gardener();
        gardener.setEmail(gardenerDto.getEmail());
        gardener.setLogin(gardenerDto.getLogin());
        gardener.setLatitude(gardenerDto.getLatitude());
        gardener.setLongitude(gardenerDto.getLongitude());
        gardener.setUserType(userTypeRepository.findById(UserType.RANCHER_USER_TYPE_ID).get());
        gardener.setPhoneNumber(gardenerDto.getPhone());
        gardener.setLastName(gardenerDto.getLastName());
        gardener.setFirstName(gardenerDto.getFirstName());
        return gardener;
    }

}

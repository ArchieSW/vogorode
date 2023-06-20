package dev.archie.rancherservice.rancher;

import dev.archie.rancherservice.landscape.CreatingUserDto;
import dev.archie.rancherservice.landscape.LandscapeService;
import dev.archie.rancherservice.landscape.User;
import dev.archie.rancherservice.rancher.dto.CreatingGardenerDto;
import dev.archie.rancherservice.rancher.exception.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GardenerService {

    private static final long RANCHER_USER_TYPE = 1;

    private final LandscapeService landscapeService;

    private final GardenerRepository gardenerRepository;

    /**
     * @param creatingGardenerDto rancher to create
     * @return created rancher's profile
     */
    public Gardener create(CreatingGardenerDto creatingGardenerDto) {
        CreatingUserDto creatingUserDto = mapCreatingGardenerDtoToUserOne(creatingGardenerDto);
        User user = landscapeService.createUser(creatingUserDto);
        Gardener gardener = mapGardenerDtoToGardener(creatingGardenerDto, user);
        return gardenerRepository.save(gardener);
    }

    /**
     * @param id of existing rancher
     * @return found profile
     */
    public Gardener getById(String id) {
        return gardenerRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(id));
    }

    /**
     * @param id of existing rancher
     * @param creatingGardenerDto new fields. New email should not exist
     * @return updated profile
     */
    public Gardener update(String id, CreatingGardenerDto creatingGardenerDto) {
        Gardener gardener = getById(id);
        CreatingUserDto creatingUserDto = mapCreatingGardenerDtoToUserOne(creatingGardenerDto);
        User user = landscapeService.update(gardener.getInnerId(), creatingUserDto);
        gardener = mapGardenerDtoToGardener(creatingGardenerDto, user);
        gardener.setId(gardener.getId());
        return gardenerRepository.save(gardener);
    }

    /**
     * @param id of existing user
     */
    public void delete(String id) {
        Gardener gardener= getById(id);
        landscapeService.delete(gardener.getInnerId());
        gardenerRepository.delete(gardener);
    }

    private static Gardener mapGardenerDtoToGardener(CreatingGardenerDto creatingGardenerDto, User user) {
        return Gardener.builder()
                .lastName(creatingGardenerDto.getLastName())
                .firstName(creatingGardenerDto.getFirstName())
                .phone(user.getPhoneNumber())
                .email(user.getEmail())
                .login(user.getLogin())
                .innerId(user.getId())
                .build();
    }

    private static CreatingUserDto mapCreatingGardenerDtoToUserOne(CreatingGardenerDto creatingGardenerDto) {
        return CreatingUserDto.builder()
                .userTypeId(RANCHER_USER_TYPE)
                .phoneNumber(creatingGardenerDto.getPhone())
                .login(creatingGardenerDto.getLogin())
                .longitude(creatingGardenerDto.getLongitude())
                .latitude(creatingGardenerDto.getLatitude())
                .email(creatingGardenerDto.getEmail())
                .build();
    }

}

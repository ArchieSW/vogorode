package dev.archie.rancherservice.rancher;

import dev.archie.rancherservice.landscape.GardenerClient;
import dev.archie.rancherservice.landscape.dto.GardenerDto;
import dev.archie.rancherservice.rancher.dto.CreatingGardenerDto;
import dev.archie.rancherservice.rancher.exception.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GardenerService {

    private final GardenerRepository gardenerRepository;
    private final GardenerClient gardenerClient;

    /**
     * @param creatingGardenerDto rancher to create
     * @return created rancher's profile
     */
    public Gardener create(CreatingGardenerDto creatingGardenerDto) {
        GardenerDto gardenerDto = gardenerClient.create(creatingGardenerDto);
        Gardener gardener = mapGardenerDtoToGardener(creatingGardenerDto, gardenerDto);
        gardener.setInnerId(gardenerDto.getId());
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
        GardenerDto user = gardenerClient.update(gardener.getInnerId(), creatingGardenerDto);
        gardener = mapGardenerDtoToGardener(creatingGardenerDto, user);
        gardener.setId(gardener.getId());
        return gardenerRepository.save(gardener);
    }

    /**
     * @param id of existing user
     */
    public void delete(String id) {
        Gardener gardener= getById(id);
        gardenerClient.delete(gardener.getInnerId());
        gardenerRepository.delete(gardener);
    }

    private static Gardener mapGardenerDtoToGardener(CreatingGardenerDto creatingGardenerDto, GardenerDto user) {
        return Gardener.builder()
                .lastName(creatingGardenerDto.getLastName())
                .firstName(creatingGardenerDto.getFirstName())
                .phone(user.getPhoneNumber())
                .email(user.getEmail())
                .login(user.getLogin())
                .innerId(user.getId())
                .build();
    }

}

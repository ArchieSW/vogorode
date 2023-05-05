package dev.archie.handymanservice.handyman;

import dev.archie.handymanservice.handyman.dto.CreatingHandymanDto;
import dev.archie.handymanservice.handyman.exception.NoSuchUserException;
import dev.archie.handymanservice.landscape.CreatingUserDto;
import dev.archie.handymanservice.landscape.LandscapeService;
import dev.archie.handymanservice.landscape.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandymanService {

    private static final long HANDYMAN_USER_TYPE_ID = 2;

    private final HandymanRepository handymanRepository;

    private final LandscapeService landscapeService;

    /**
     * @param creatingHandymanDto handyman dto create. Email should not exist
     * @return Created profile
     */
    public Profile create(CreatingHandymanDto creatingHandymanDto) {
        CreatingUserDto creatingUserDto = mapCreatingHandymanDtoToUserOne(creatingHandymanDto);
        User createdUser = landscapeService.createUser(creatingUserDto);
        Handyman handyman = mapUserToHandyman(createdUser, creatingHandymanDto);
        handyman.setInnerId(createdUser.getId());
        handymanRepository.insert(handyman);
        return new Profile(handyman, createdUser);
    }

    /**
     * @param id for existing profile
     * @return found profile
     */
    public Profile getProfileById(String id) {
        Handyman handyman = getById(id);
        User user = landscapeService.getById(handyman.getInnerId());
        return new Profile(handyman, user);
    }

    /**
     * @param id for existing handyman
     * @return found handyman
     */
    public Handyman getById(String id) {
        return handymanRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(id));
    }

    /**
     * @param id of existing profile
     * @param creatingHandymanDto of new fields. New email should not exist
     * @return Profile od updated handyman
     */
    public Profile update(String id, CreatingHandymanDto creatingHandymanDto) {
        Handyman handyman = getById(id);
        CreatingUserDto creatingUserDto = mapCreatingHandymanDtoToUserOne(creatingHandymanDto);
        User updatedUser = landscapeService.update(handyman.getInnerId(), creatingUserDto);
        Handyman updatedHandyman = mapUserToHandyman(updatedUser, creatingHandymanDto);
        updatedHandyman.setId(handyman.getId());
        handymanRepository.save(updatedHandyman);
        return new Profile(updatedHandyman, updatedUser);
    }

    /**
     * @param id of existing handyman
     */
    public void delete(String id) {
        Handyman handyman = getById(id);
        landscapeService.delete(handyman.getInnerId());
        handymanRepository.delete(handyman);
    }

    private CreatingUserDto mapCreatingHandymanDtoToUserOne(CreatingHandymanDto creatingHandymanDto) {
        return CreatingUserDto.builder()
                .email(creatingHandymanDto.getEmail())
                .login(creatingHandymanDto.getLogin())
                .longitude(creatingHandymanDto.getLongitude())
                .latitude(creatingHandymanDto.getLatitude())
                .phoneNumber(creatingHandymanDto.getPhoneNumber())
                .userTypeId(HANDYMAN_USER_TYPE_ID)
                .build();
    }

    private Handyman mapUserToHandyman(User createdUser, CreatingHandymanDto creatingHandymanDto) {
        return Handyman.builder()
                .longitude(createdUser.getLongitude())
                .latitude(createdUser.getLatitude())
                .innerId(createdUser.getId())
                .skills(creatingHandymanDto.getSkills())
                .build();
    }
}

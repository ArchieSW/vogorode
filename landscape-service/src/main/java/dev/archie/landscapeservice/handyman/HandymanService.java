package dev.archie.landscapeservice.handyman;

import dev.archie.landscapeservice.handyman.dto.CreatingHandymanDto;
import dev.archie.landscapeservice.user.exceptions.NoSuchUserException;
import dev.archie.landscapeservice.user.type.UserType;
import dev.archie.landscapeservice.user.type.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HandymanService {

    private final HandymanRepository handymanRepository;
    private final UserTypeRepository userTypeRepository;

    public Handyman create(CreatingHandymanDto creatingHandymanDto) {
        Handyman handyman = mapCreatingHandymanDtoToHandyman(creatingHandymanDto);
        handyman.setCreatedAt(LocalDateTime.now());
        handyman.setLastUpdatedAt(LocalDateTime.now());
        return handymanRepository.save(handyman);
    }

    public Handyman getById(UUID id) {
        return handymanRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(id));
    }

    public Handyman update(CreatingHandymanDto handymanDto, UUID id) {
        Handyman handyman = getById(id);
        Handyman updated = mapCreatingHandymanDtoToHandyman(handymanDto);
        handyman.setEmail(updated.getEmail());
        handyman.setFirstName(updated.getFirstName());
        handyman.setLastName(updated.getLastName());
        handyman.setLatitude(updated.getLatitude());
        handyman.setLongitude(updated.getLongitude());
        handyman.setLogin(updated.getLogin());
        handyman.setPhoneNumber(updated.getPhoneNumber());
        handyman.setLastUpdatedAt(LocalDateTime.now());
        return handymanRepository.save(handyman);
    }

    public void delete(UUID id) {
        handymanRepository.delete(getById(id));
    }

    private Handyman mapCreatingHandymanDtoToHandyman(CreatingHandymanDto creatingHandymanDto) {
        Handyman handyman = new Handyman();
        handyman.setEmail(creatingHandymanDto.getEmail());
        handyman.setFirstName(creatingHandymanDto.getFirstName());
        handyman.setLastName(creatingHandymanDto.getLastName());
        handyman.setLatitude(creatingHandymanDto.getLatitude());
        handyman.setLongitude(creatingHandymanDto.getLongitude());
        handyman.setLogin(creatingHandymanDto.getLogin());
        handyman.setUserType(userTypeRepository.findById(UserType.HANDYMAN_USER_TYPE_ID).get());
        handyman.setPhoneNumber(creatingHandymanDto.getPhoneNumber());
        return handyman;
    }

}

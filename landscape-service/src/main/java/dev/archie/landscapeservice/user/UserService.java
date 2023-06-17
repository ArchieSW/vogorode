package dev.archie.landscapeservice.user;

import dev.archie.landscapeservice.user.dto.CreatingUserDto;
import dev.archie.landscapeservice.user.exceptions.NoSuchUserException;
import dev.archie.landscapeservice.user.exceptions.NoSuchUserTypeException;
import dev.archie.landscapeservice.user.exceptions.UserAlreadyExistsException;
import dev.archie.landscapeservice.user.exceptions.UserDoesNotExists;
import dev.archie.landscapeservice.user.type.UserType;
import dev.archie.landscapeservice.user.type.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserTypeRepository userTypeRepository;

    /**
     * @param creatingUserDto user to create
     * @return created user
     */
    public User create(CreatingUserDto creatingUserDto) {
        UserType userType = getUserTypeById(creatingUserDto.getUserTypeId());
        String email = creatingUserDto.getEmail();
        userRepository.findByEmail(email)
                .ifPresent((user) -> {
                    throw new UserAlreadyExistsException(user.getEmail());
                });
        User user = createUserFromCreatingUserDto(creatingUserDto, userType);
        return userRepository.save(user);
    }

    /**
     * @param id of existing user
     * @return found user
     */
    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(id));
    }

    /**
     * @param id of existing user
     * @param creatingUserDto new fields for user. New email should not exist
     * @return updated user
     */
    public User update(UUID id, CreatingUserDto creatingUserDto) {
        User user = getById(id);
        String email = creatingUserDto.getEmail();
        Optional<User> possibleUserByNewEmail = userRepository.findByEmail(email);
        if (possibleUserByNewEmail.isPresent() && !possibleUserByNewEmail.get().equals(user)) {
            throw new UserAlreadyExistsException(email);
        }
        UserType userType = getUserTypeById(creatingUserDto.getUserTypeId());
        User updatedUser = createUserFromCreatingUserDto(creatingUserDto, userType);
        updatedUser.setId(user.getId());
        return userRepository.save(updatedUser);
    }

    /**
     * @param id of existing user to delete
     */
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserDoesNotExists(id);
        }
        userRepository.deleteById(id);
    }

    /**
     * @return available user types
     */
    public List<UserType> getUserTypes() {
        return userTypeRepository.findAll();
    }

    private UserType getUserTypeById(Long userTypeId) {
        return userTypeRepository.findById(userTypeId)
                .orElseThrow(() -> new NoSuchUserTypeException(userTypeId));
    }

    private User createUserFromCreatingUserDto(CreatingUserDto creatingUserDto, UserType userType) {
        return User.builder()
                .phoneNumber(creatingUserDto.getPhoneNumber())
                .login(creatingUserDto.getLogin())
                .email(creatingUserDto.getEmail())
                .createdAt(LocalDateTime.now())
                .lastUpdatedAt(LocalDateTime.now())
                .userType(userType)
                .longitude(creatingUserDto.getLongitude())
                .latitude(creatingUserDto.getLatitude())
                .build();
    }

    public Page<User> getAllHandymen(int pageSize, int pageNumber) {
        PageRequest request = PageRequest.of(pageNumber, pageSize, Sort.by("email"));
        UserType handymanUserType = userTypeRepository.findById(UserType.HANDYMAN_USER_TYPE_ID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT));
        return userRepository.findByUserType(handymanUserType, request);
    }

    public Page<User> getAllRanchers(int pageSize, int pageNumber) {
        PageRequest request = PageRequest.of(pageNumber, pageSize, Sort.by("email"));
        UserType rancherUserType = userTypeRepository.findById(UserType.RANCHER_USER_TYPE_ID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT));
        return userRepository.findByUserType(rancherUserType, request);
    }
}

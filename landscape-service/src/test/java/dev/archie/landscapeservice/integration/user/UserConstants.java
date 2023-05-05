package dev.archie.landscapeservice.integration.user;

import dev.archie.landscapeservice.user.User;
import dev.archie.landscapeservice.user.dto.CreatingUserDto;
import dev.archie.landscapeservice.user.type.UserType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserConstants {


    public static final UserType RANCHER = UserType.builder()
            .id(1L)
            .type("rancher")
            .build();
    public static final UserType HANDYMAN = UserType.builder()
            .id(2L)
            .type("handyman")
            .build();
    public static final List<UserType> USER_TYPES = List.of(RANCHER, HANDYMAN);

    public static final User FIRST_MOCK_USER_IN_DB = User.builder()
            .id(UUID.fromString("fa80750a-d06f-4e15-b50a-08fcd091a543"))
            .login("archie")
            .email("archie@ya.ru")
            .phoneNumber("+79371233221")
            .createdAt(LocalDateTime.parse("2023-04-04T23:53:00.528948685"))
            .lastUpdatedAt(LocalDateTime.parse("2023-04-04T23:53:00.528948685"))
            .longitude(1.1)
            .latitude(2.3)
            .userType(RANCHER)
            .build();

    public static final User SECOND_MOCK_USER_IN_DB = User.builder()
            .id(UUID.fromString("53b04327-f926-46e4-9b87-9fbea29bb2f2"))
            .login("archibald")
            .email("archiensky@ya.ru")
            .phoneNumber("+79371233221")
            .createdAt(LocalDateTime.parse("2023-04-04T23:53:00.528948685"))
            .lastUpdatedAt(LocalDateTime.parse("2023-04-04T23:53:00.528948685"))
            .userType(HANDYMAN)
            .longitude(2.1)
            .latitude(4.4)
            .build();

    public static final CreatingUserDto USER_DTO_TO_SAVE = CreatingUserDto.builder()
            .login("userToSave")
            .email("user@mail.ru")
            .phoneNumber("+71233212321")
            .userTypeId(RANCHER.getId())
            .longitude(1.1)
            .latitude(2.3)
            .build();

    public static final CreatingUserDto USER_DTO_WITH_EXISTING_EMAIL = CreatingUserDto.builder()
            .userTypeId(UserConstants.RANCHER.getId())
            .login("archiensky")
            .phoneNumber("+71233212332")
            .email(UserConstants.FIRST_MOCK_USER_IN_DB.getEmail())
            .longitude(2.1)
            .latitude(4.4)
            .build();
}

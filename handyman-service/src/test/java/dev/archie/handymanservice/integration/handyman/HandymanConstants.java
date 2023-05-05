package dev.archie.handymanservice.integration.handyman;

import dev.archie.handymanservice.handyman.dto.CreatingHandymanDto;
import dev.archie.handymanservice.landscape.User;

import java.util.List;

public class HandymanConstants {

    public static final CreatingHandymanDto CREATING_PROFILE_DTO = CreatingHandymanDto.builder()
            .login("archik")
            .email("arhcik@ya.ru")
            .phoneNumber("+123321123")
            .latitude(2.0)
            .longitude(3.0)
            .skills(List.of("skill1", "skill2"))
            .build();

    public static final User MOCK_RESPONSE_USER = User.builder().build();

}

package dev.archie.rancherservice.integration.rancher;

import dev.archie.rancherservice.landscape.User;
import dev.archie.rancherservice.rancher.dto.CreatingGardenerDto;

import java.util.List;

public class RancherConstants {

    public static final CreatingGardenerDto CREATING_PROFILE_DTO = CreatingGardenerDto.builder()
            .login("archik")
            .email("arhcik@ya.ru")
            .phone("+123321123")
            .latitude(2.0)
            .longitude(3.0)
            .area(12.0)
            .jobs(List.of("сажают", "поливают"))
            .build();

    public static final  User MOCK_RESPONSE_USER = User.builder().build();

}

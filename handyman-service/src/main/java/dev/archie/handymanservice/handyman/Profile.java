package dev.archie.handymanservice.handyman;

import dev.archie.handymanservice.landscape.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Profile {

    Handyman handyman;

    User user;

}

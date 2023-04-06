package dev.archie.rancherservice.rancher;

import dev.archie.rancherservice.landscape.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Profile {

    private Plot plot;

    private User rancher;
}

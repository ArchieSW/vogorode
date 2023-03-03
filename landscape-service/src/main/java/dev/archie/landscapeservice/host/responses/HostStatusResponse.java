package dev.archie.landscapeservice.host.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HostStatusResponse {

    private String host;
    private String status;
    private String artifact;
    private String name;
    private String group;
    private String version;
}

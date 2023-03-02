package dev.archie.landscapeservice.host.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class HostStatusResponse {

    private String host;
    private String status;
    private String artifact;
    private String name;
    private String group;
    private String version;
}

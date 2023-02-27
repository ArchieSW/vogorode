package dev.archie.landscapeservice.host.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class HostStatusDto {

    private String host;
    private String status;
    private String artifact;
    private String name;
    private String group;
    private String version;
}

package dev.archie.landscapeservice.integration.common;

import dev.archie.landscapeservice.ReadinessResponse;
import dev.archie.landscapeservice.VersionResponse;
import dev.archie.landscapeservice.host.dto.HostStatusDto;

public class TestConstants {

    public static final String HANDYMAN_SERVICE_NAME = "handyman-service";
    public static final String RANCHER_SERVICE_NAME = "rancher-service";
    public static final String SERVICE_NAME = "landscape-service";
    public static final String GROUP_ID = "dev.archie";
    public static final VersionResponse HANDYMAN_VERSION = VersionResponse.newBuilder()
        .setName(HANDYMAN_SERVICE_NAME)
        .setVersion("0.0.1")
        .setArtifact(HANDYMAN_SERVICE_NAME)
        .setGroup(GROUP_ID)
        .build();
    public static final String HANDYMAN_HOST = "localhost:9090";
    public static final ReadinessResponse HANDYMAN_READINESS = ReadinessResponse.newBuilder()
        .setStatus("READY")
        .build();
    public static final HostStatusDto HANDYMAN_HOST_STATUS = HostStatusDto.builder()
        .status(HANDYMAN_READINESS.getStatus())
        .host(HANDYMAN_HOST)
        .group(HANDYMAN_VERSION.getGroup())
        .artifact(HANDYMAN_VERSION.getArtifact())
        .version(HANDYMAN_VERSION.getVersion())
        .name(HANDYMAN_VERSION.getName())
        .build();
    public static final VersionResponse RANCHER_VERSION = VersionResponse.newBuilder()
        .setName(RANCHER_SERVICE_NAME)
        .setArtifact(RANCHER_SERVICE_NAME)
        .setGroup(GROUP_ID)
        .setVersion("0.0.2")
        .build();
    public static final ReadinessResponse RANCHER_READINESS = ReadinessResponse.newBuilder()
        .setStatus("IDLE")
        .build();
    public static final String RANCHER_HOST = "localhost:9091";
    public static final HostStatusDto RANCHER_HOST_STATUS = HostStatusDto.builder()
        .status(RANCHER_READINESS.getStatus())
        .host(RANCHER_HOST)
        .group(RANCHER_VERSION.getGroup())
        .artifact(RANCHER_VERSION.getArtifact())
        .version(RANCHER_VERSION.getVersion())
        .name(RANCHER_VERSION.getName())
        .build();
}

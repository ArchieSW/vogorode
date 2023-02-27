package dev.archie.rancherservice.integration.common;

public class ServiceConstants {

    public static final String SERVICE_NAME = "rancher-service";
    public static final String[] READINESS_VALUES = {"IDLE", "READY", "CONNECTING",
        "TRANSIENT_FAILURE", "SHUTDOWN"};
}

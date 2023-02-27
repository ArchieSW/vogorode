package dev.archie.handymanservice.integration.common;

public class ServiceConstants {

    public static final String SERVICE_NAME = "handyman-service";
    public static final String[] READINESS_VALUES = {"IDLE", "READY", "CONNECTING",
        "TRANSIENT_FAILURE", "SHUTDOWN"};
}

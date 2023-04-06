package dev.archie.handymanservice.handyman.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnableToConnectToInnerService extends ResponseStatusException {

    public UnableToConnectToInnerService() {
        super(HttpStatus.SERVICE_UNAVAILABLE, "Unable to connect to inner service");
    }
}

package dev.archie.rancherservice.gardener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GardenerAlreadyExistsException extends ResponseStatusException {

    public GardenerAlreadyExistsException(String email) {
        super(HttpStatus.BAD_REQUEST, "Gardener with such email already exists: " + email);
    }
}

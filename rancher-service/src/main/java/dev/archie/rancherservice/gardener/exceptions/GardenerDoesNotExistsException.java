package dev.archie.rancherservice.gardener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GardenerDoesNotExistsException extends ResponseStatusException {

    public GardenerDoesNotExistsException(Long id) {
        super(HttpStatus.NOT_FOUND, "Gardener with such id not found: " + id);
    }

}

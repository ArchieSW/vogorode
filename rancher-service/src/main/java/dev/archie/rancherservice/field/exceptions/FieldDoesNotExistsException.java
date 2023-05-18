package dev.archie.rancherservice.field.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FieldDoesNotExistsException extends ResponseStatusException {
    public FieldDoesNotExistsException(Long id) {
        super(HttpStatus.NOT_FOUND, "No such gardener with id: " + id);
    }
}

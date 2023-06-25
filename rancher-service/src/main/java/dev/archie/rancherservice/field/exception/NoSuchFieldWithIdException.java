package dev.archie.rancherservice.field.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchFieldWithIdException extends ResponseStatusException {
    public NoSuchFieldWithIdException(String id) {
        super(HttpStatus.NOT_FOUND, "No such field with id: " + id);
    }
}

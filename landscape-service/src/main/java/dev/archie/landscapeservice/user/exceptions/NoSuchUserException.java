package dev.archie.landscapeservice.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class NoSuchUserException extends ResponseStatusException {
    public NoSuchUserException(UUID id) {
        super(HttpStatus.NOT_FOUND, "No such user with id: " + id);
    }
}

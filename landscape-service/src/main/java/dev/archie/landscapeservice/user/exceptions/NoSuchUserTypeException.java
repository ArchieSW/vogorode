package dev.archie.landscapeservice.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchUserTypeException extends ResponseStatusException {

    public NoSuchUserTypeException(Long id) {
        super(HttpStatus.BAD_REQUEST, "There is no such user with id = " + id);
    }
}

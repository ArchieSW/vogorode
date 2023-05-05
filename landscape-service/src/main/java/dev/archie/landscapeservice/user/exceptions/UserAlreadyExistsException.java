package dev.archie.landscapeservice.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends ResponseStatusException {

    public UserAlreadyExistsException(String email) {
        super(HttpStatus.BAD_REQUEST, "User with such email already exists: " + email);
    }
}

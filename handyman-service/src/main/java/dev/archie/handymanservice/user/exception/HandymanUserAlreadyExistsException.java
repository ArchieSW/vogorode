package dev.archie.handymanservice.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HandymanUserAlreadyExistsException extends ResponseStatusException {

    public HandymanUserAlreadyExistsException(String email) {
        super(HttpStatus.BAD_REQUEST, "HandymanUser with such email already exists: " + email);
    }
}

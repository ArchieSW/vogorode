package dev.archie.landscapeservice.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class UserDoesNotExists extends ResponseStatusException {
    public UserDoesNotExists(UUID id) {
        super(HttpStatus.NOT_FOUND, "User with such id does not exists: " + id);
    }
}

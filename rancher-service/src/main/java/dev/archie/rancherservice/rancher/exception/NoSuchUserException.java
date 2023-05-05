package dev.archie.rancherservice.rancher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchUserException extends ResponseStatusException {
    public NoSuchUserException(String id) {
        super(HttpStatus.NOT_FOUND, "No such user with id: " + id);
    }
}

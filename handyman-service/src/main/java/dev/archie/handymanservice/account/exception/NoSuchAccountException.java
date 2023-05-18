package dev.archie.handymanservice.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchAccountException extends ResponseStatusException {

    public NoSuchAccountException(Long id) {
        super(HttpStatus.NOT_FOUND, "No such account with id: " + id);
    }
}

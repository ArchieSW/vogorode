package dev.archie.landscapeservice.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchOrderException extends ResponseStatusException {
    public NoSuchOrderException(Long id) {
        super(HttpStatus.NOT_FOUND, "No such order with id: " + id);
    }
}

package dev.archie.landscapeservice.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DirectionIsNotSpecifiedException extends ResponseStatusException {
    public DirectionIsNotSpecifiedException(String direction) {
        super(HttpStatus.BAD_REQUEST,
                "No direction specified for sorted request. Expected \"ASC\" or \"DESC\" but got: " + direction);
    }
}

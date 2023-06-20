package dev.archie.handymanservice.handyman.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchHandymanUserException extends ResponseStatusException {
    public NoSuchHandymanUserException(String handymanUserId) {
        super(HttpStatus.NOT_FOUND, "No such handyman user with id: " + handymanUserId);
    }
}

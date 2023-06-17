package dev.archie.handymanservice.account.bank;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchBankException extends ResponseStatusException {
    public NoSuchBankException(String name) {
        super(HttpStatus.NOT_FOUND, "No such bank with name: " + name);
    }
}

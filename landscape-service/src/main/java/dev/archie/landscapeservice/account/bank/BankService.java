package dev.archie.landscapeservice.account.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;

    public Bank getBankByName(String bankName) {
        return bankRepository.findByName(bankName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No such bank with name " + bankName));
    }
}

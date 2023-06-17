package dev.archie.handymanservice.account.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    public Bank getBankByName(String name) {
        return bankRepository.findByName(name)
                .orElseThrow(() -> new NoSuchBankException(name));
    }

}


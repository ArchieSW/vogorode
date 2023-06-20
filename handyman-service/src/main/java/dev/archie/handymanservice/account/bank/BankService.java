package dev.archie.handymanservice.account.bank;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BankService {

    private static final Bank[] BANKS = new Bank[]{
            Bank.builder().name("InterGalactic Banking Clan").build(),
            Bank.builder().name("Gringotts").build(),
            Bank.builder().name("ScaminaBank").build(),
            Bank.builder().name("Trinance").build(),
            Bank.builder().name("StonksBank").build(),
            Bank.builder().name("DogeBank").build(),
            Bank.builder().name("PepegaBank").build(),
            Bank.builder().name("ToTheMoonBank").build(),
            Bank.builder().name("RickRollBank").build(),
            Bank.builder().name("YetAnotherBank").build()
    };

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
        Arrays.stream(BANKS)
                .forEach(bank -> bankRepository.findByName(bank.getName())
                .orElseGet(() -> bankRepository.save(bank)));
    }

    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    public Bank getBankByName(String name) {
        return bankRepository.findByName(name)
                .orElseThrow(() -> new NoSuchBankException(name));
    }

}


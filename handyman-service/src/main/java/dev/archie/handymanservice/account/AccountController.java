package dev.archie.handymanservice.account;

import dev.archie.handymanservice.account.bank.Bank;
import dev.archie.handymanservice.account.bank.BankService;
import dev.archie.handymanservice.account.dto.CreatingAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;
    private final BankService bankService;

    @PostMapping("/{handymanUserId}")
    public Account create(@RequestBody CreatingAccountDto accountDto, @PathVariable String handymanUserId) {
        return accountService.create(accountDto, handymanUserId);
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable String id) {
        return accountService.getById(id);
    }

    @PutMapping("/{id}")
    public Account update(@RequestBody CreatingAccountDto accountDto, @PathVariable String id) {
        return accountService.update(accountDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        accountService.delete(id);
    }

    @GetMapping("/banks")
    public List<Bank> getAllBanks() {
        return bankService.getAll();
    }

    @GetMapping("/banks/{name}")
    public Bank getBankByName(@PathVariable String name) {
        return bankService.getBankByName(name);
    }

}

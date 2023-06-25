package dev.archie.landscapeservice.account;

import dev.archie.landscapeservice.account.dto.CreatingAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{handymanId}")
    public Account create(@RequestBody CreatingAccountDto creatingAccountDto,
                          @PathVariable UUID handymanId) {
        return accountService.create(creatingAccountDto, handymanId);
    }

    @PutMapping("/{accountId}")
    public Account update(@RequestBody CreatingAccountDto creatingAccountDto,
                          @PathVariable Long accountId) {
        return accountService.update(creatingAccountDto, accountId);
    }

    @GetMapping("/{accountId}")
    public Account getById(@PathVariable Long accountId) {
        return accountService.getById(accountId);
    }

    @DeleteMapping("/{accountId}")
    public void delete(@PathVariable Long accountId) {
        accountService.delete(accountId);
    }

}

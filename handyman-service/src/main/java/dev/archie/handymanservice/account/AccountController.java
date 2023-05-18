package dev.archie.handymanservice.account;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{handymanUserId}")
    public Account create(@RequestBody CreatingAccountDto accountDto, @PathVariable Long handymanUserId) {
        return accountService.create(accountDto, handymanUserId);
    }

    @GetMapping("/{id}")
    public Account getById(Long id) {
        return accountService.getById(id);
    }

    @PutMapping("/{id}")
    public Account update(@RequestBody CreatingAccountDto accountDto, @PathVariable Long id) {
        return accountService.update(accountDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }

}

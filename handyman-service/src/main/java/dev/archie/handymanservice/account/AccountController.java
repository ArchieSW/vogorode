package dev.archie.handymanservice.account;

import dev.archie.handymanservice.account.dto.CreatingAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{handymanId}")
    public Account create(@RequestBody CreatingAccountDto accountDto,
                          @PathVariable String handymanId) {
        return accountService.create(accountDto, handymanId);
    }

    @GetMapping("/{id}/{handymanId}")
    public Account getById(@PathVariable int id,
                           @PathVariable String handymanId) {
        return accountService.getAccountById(id, handymanId);
    }

    @PutMapping("/{id}/{handymanId}")
    public Account update(@RequestBody CreatingAccountDto accountDto,
                          @PathVariable int id,
                          @PathVariable String handymanId) {
        return accountService.update(accountDto, id, handymanId);
    }

    @DeleteMapping("/{id}/{handymanId}")
    public void delete(@PathVariable int id, @PathVariable String handymanId) {
        accountService.delete(id, handymanId);
    }

}

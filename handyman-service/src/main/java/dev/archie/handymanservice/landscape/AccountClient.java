package dev.archie.handymanservice.landscape;

import dev.archie.handymanservice.account.Account;
import dev.archie.handymanservice.account.dto.CreatingAccountDto;
import dev.archie.handymanservice.landscape.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "account-service", url = "${application.landscape.rest.url}", path = "/accounts")
public interface AccountClient {

    @PostMapping("/{handymanId}")
    AccountDto create(@RequestBody CreatingAccountDto creatingAccountDto,
                      @PathVariable UUID handymanId);

    @PutMapping("/{accountId}")
    AccountDto update(@RequestBody CreatingAccountDto creatingAccountDto,
                   @PathVariable Long accountId);

    @GetMapping("/{accountId}")
    AccountDto getById(@PathVariable Long accountId);

    @DeleteMapping("/{accountId}")
    void delete(@PathVariable Long accountId);
}

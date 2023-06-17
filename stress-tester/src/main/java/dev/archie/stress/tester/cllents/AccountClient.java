package dev.archie.stress.tester.cllents;

import dev.archie.stress.tester.dto.CreatingAccountDto;
import dev.archie.stress.tester.entities.Account;
import dev.archie.stress.tester.entities.Bank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "account-client", url = "${handyman-service-url}", path = "/accounts")
public interface AccountClient {

    @PostMapping("/{handymanUserId}")
    Account create(@RequestBody CreatingAccountDto accountDto, @PathVariable Long handymanUserId);

    @GetMapping("/{id}")
    Account getById(Long id);

    @PutMapping("/{id}")
    Account update(@RequestBody CreatingAccountDto accountDto, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

    @GetMapping("/banks")
    List<Bank> getAllBanks();

    @GetMapping("/banks/{name}")
    Bank getBankByName(@PathVariable String name);

}


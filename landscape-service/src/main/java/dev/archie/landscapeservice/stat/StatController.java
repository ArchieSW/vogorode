package dev.archie.landscapeservice.stat;

import dev.archie.landscapeservice.account.bank.BankRepository;
import dev.archie.landscapeservice.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatController {
    private final UserRepository userRepository;
    private final BankRepository bankRepository;

    @GetMapping("/ranchers")
    public Page<RancherStat> getRancherStatGroupByLogin(@RequestParam(name = "number") int pageNumber,
                                                        @RequestParam(name = "size") int pageSize) {
        PageRequest request = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        return userRepository.findRancherStatGroupByLogin(request);
    }

    @GetMapping("/banks")
    public List<BankStat> getBankStats() {
        return bankRepository.findAllBankStat();
    }
}

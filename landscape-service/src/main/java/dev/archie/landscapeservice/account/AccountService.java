package dev.archie.landscapeservice.account;

import dev.archie.landscapeservice.account.bank.BankService;
import dev.archie.landscapeservice.account.dto.CreatingAccountDto;
import dev.archie.landscapeservice.account.exception.NoSuchAccountException;
import dev.archie.landscapeservice.handyman.Handyman;
import dev.archie.landscapeservice.handyman.HandymanRepository;
import dev.archie.landscapeservice.user.exceptions.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final HandymanRepository handymanUserRepository;
    private final BankService bankService;

    public Account create(CreatingAccountDto accountDto, UUID handymanUserId) {
        Handyman user = getHandymanById(handymanUserId);
        Account account = mapCreatingAccountDtoToAccount(accountDto, user);
        account.setBank(bankService.getBankByName(accountDto.getBankName()));
        return accountRepository.save(account);
    }

    public Account getById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchAccountException(id));
    }

    public Account update(CreatingAccountDto accountDto, Long id) {
        Account account = getById(id);
        Account updatedAccount = mapCreatingAccountDtoToAccount(accountDto, account.getHandyman());
        account.setBank(bankService.getBankByName(accountDto.getBankName()));
        updatedAccount.setId(id);
        return accountRepository.save(updatedAccount);
    }

    public void delete(Long id) {
        accountRepository.delete(getById(id));
    }

    private Handyman getHandymanById(UUID handymanUserId) {
        return handymanUserRepository.findById(handymanUserId)
                .orElseThrow(() -> new NoSuchUserException(handymanUserId));
    }

    private Account mapCreatingAccountDtoToAccount(CreatingAccountDto accountDto, Handyman user) {
        return Account.builder()
                .handyman(user)
                .cardNumber(accountDto.getCardNumber())
                .paymentSystem(accountDto.getPaymentSystem())
                .build();
    }

}

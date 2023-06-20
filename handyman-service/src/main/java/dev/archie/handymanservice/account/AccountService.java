package dev.archie.handymanservice.account;

import dev.archie.handymanservice.account.bank.BankService;
import dev.archie.handymanservice.account.dto.CreatingAccountDto;
import dev.archie.handymanservice.account.exception.NoSuchAccountException;
import dev.archie.handymanservice.handyman.Handyman;
import dev.archie.handymanservice.handyman.HandymanRepository;
import dev.archie.handymanservice.handyman.exception.NoSuchHandymanUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final HandymanRepository handymanRepository;
    private final BankService bankService;

    public Account create(CreatingAccountDto accountDto, String handymanUserId) {
        Handyman user = getHandymanUserById(handymanUserId);
        Account account = mapCreatingAccountDtoToAccount(accountDto, user);
        account.setBank(bankService.getBankByName(accountDto.getBankName()));
        return accountRepository.save(account);
    }

    public Account getById(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchAccountException(id));
    }

    public Account update(CreatingAccountDto accountDto, String id) {
        Account account = getById(id);
        Account updatedAccount = mapCreatingAccountDtoToAccount(accountDto, account.getHandyman());
        account.setBank(bankService.getBankByName(accountDto.getBankName()));
        updatedAccount.setId(id);
        return accountRepository.save(updatedAccount);
    }

    public void delete(String id) {
        accountRepository.delete(getById(id));
    }

    private Handyman getHandymanUserById(String handymanUserId) {
        return handymanRepository.findById(handymanUserId)
                .orElseThrow(() -> new NoSuchHandymanUserException(handymanUserId));
    }

    private Account mapCreatingAccountDtoToAccount(CreatingAccountDto accountDto,
                                                   Handyman user) {
        return Account.builder()
                .handyman(user)
                .cardNumber(accountDto.getCardNumber())
                .paymentSystem(accountDto.getPaymentSystem())
                .build();
    }

}

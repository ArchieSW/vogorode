package dev.archie.handymanservice.account;

import dev.archie.handymanservice.account.bank.BankService;
import dev.archie.handymanservice.account.dto.CreatingAccountDto;
import dev.archie.handymanservice.account.exception.NoSuchAccountException;
import dev.archie.handymanservice.handyman.Handyman;
import dev.archie.handymanservice.handyman.HandymanRepository;
import dev.archie.handymanservice.handyman.exception.NoSuchHandymanUserException;
import dev.archie.handymanservice.landscape.AccountClient;
import dev.archie.handymanservice.landscape.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountClient accountClient;
    private final HandymanRepository handymanRepository;
    private final BankService bankService;

    public Account create(CreatingAccountDto accountDto, String handymanUserId) {
        Handyman user = getHandymanUserById(handymanUserId);
        AccountDto registeredAccount = accountClient.create(accountDto, user.getInnerId());
        Account account = mapCreatingAccountDtoToAccount(accountDto, user);
        account.setInnerId(registeredAccount.getId());
        account.setHandyman(user);
        account.setBank(bankService.getBankByName(accountDto.getBankName()));

        account = accountRepository.save(account);
        user.getAccounts().add(account);
        handymanRepository.save(user);
        return account;
    }

    public Account getById(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchAccountException(id));
    }

    public Account update(CreatingAccountDto accountDto, String id) {
        Account account = getById(id);
        accountClient.update(accountDto, account.getInnerId());
        Account updatedAccount = mapCreatingAccountDtoToAccount(accountDto, account.getHandyman());
        updatedAccount.setId(account.getId());
        updatedAccount.setInnerId(account.getInnerId());
        updatedAccount.setBank(bankService.getBankByName(accountDto.getBankName()));
        updatedAccount.setHandyman(account.getHandyman());
        return accountRepository.save(updatedAccount);
    }

    public void delete(String id) {
        Account account = getById(id);
        accountClient.delete(account.getInnerId());
        accountRepository.delete(account);
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

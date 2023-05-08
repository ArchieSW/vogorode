package dev.archie.handymanservice.account;

import dev.archie.handymanservice.account.dto.CreatingAccountDto;
import dev.archie.handymanservice.account.exception.NoSuchAccountException;
import dev.archie.handymanservice.handyman.exception.NoSuchHandymanUserException;
import dev.archie.handymanservice.user.HandymanUser;
import dev.archie.handymanservice.user.HandymanUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final HandymanUserRepository handymanUserRepository;

    public Account create(CreatingAccountDto accountDto, Long handymanUserId) {
        HandymanUser user = getHandymanUserById(handymanUserId);
        Account account = mapCreatingAccountDtoToAccount(accountDto, user);
        System.out.println(accountDto);
        System.out.println(account);
        return accountRepository.save(account);
    }

    public Account getById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchAccountException(id));
    }

    public Account update(CreatingAccountDto accountDto, Long id) {
        Account account = getById(id);
        Account updatedAccount = mapCreatingAccountDtoToAccount(accountDto, account.getHandymanUser());
        updatedAccount.setId(id);
        return accountRepository.save(updatedAccount);
    }

    public void delete(Long id) {
        accountRepository.delete(getById(id));
    }

    private HandymanUser getHandymanUserById(Long handymanUserId) {
        return handymanUserRepository.findById(handymanUserId)
                .orElseThrow(() -> new NoSuchHandymanUserException(handymanUserId));
    }

    private Account mapCreatingAccountDtoToAccount(CreatingAccountDto accountDto, HandymanUser user) {
        return Account.builder()
                .handymanUser(user)
                .cardNumber(accountDto.getCardNumber())
                .paymentSystem(accountDto.getPaymentSystem())
                .build();
    }

}

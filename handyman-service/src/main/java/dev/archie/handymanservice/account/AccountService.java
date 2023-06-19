package dev.archie.handymanservice.account;

import dev.archie.handymanservice.account.bank.Bank;
import dev.archie.handymanservice.account.dto.CreatingAccountDto;
import dev.archie.handymanservice.account.exception.NoSuchAccountException;
import dev.archie.handymanservice.handyman.Handyman;
import dev.archie.handymanservice.handyman.HandymanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final HandymanService handymanService;

    public Account create(CreatingAccountDto accountDto, String handymanUserId) {
        Handyman user = getHandymanById(handymanUserId);
        Account account = mapCreatingAccountDtoToAccount(accountDto);
        int maxId = getMaxId(user);
        account.setId(maxId + 1);
        user.getAccounts().add(account);
        handymanService.update(user);
        return account;
    }

    public Account getAccountById(int id, String handymanId) {
        Handyman user = getHandymanById(handymanId);
        return getAccountById(id, user);
    }

    public Account getAccountById(int id, Handyman handyman) {
        return handyman.getAccounts().stream().filter(currentAccount -> currentAccount.getId() == id).findFirst().orElseThrow(() -> new NoSuchAccountException((long) id));
    }

    public Account update(CreatingAccountDto accountDto, int id, String handymanId) {
        Handyman handyman = handymanService.getById(handymanId);
        Account account = getAccountById(id, handyman);
        Account updatedAccount = mapCreatingAccountDtoToAccount(accountDto);
        copyAccount(account, updatedAccount);
        handymanService.update(handyman);
        return account;
    }

    public void delete(int id, String handymanId) {
        Handyman handyman = getHandymanById(handymanId);
        boolean removed = handyman.getAccounts().removeIf(account -> account.getId() == id);
        if (!removed) {
            throw new NoSuchAccountException((long) id);
        }
        handymanService.update(handyman);
    }

    private int getMaxId(Handyman user) {
        return user.getAccounts().stream()
                .mapToInt(Account::getId)
                .max()
                .orElse(0);
    }

    private Handyman getHandymanById(String handymanId) {
        return handymanService.getById(handymanId);
    }

    private void copyAccount(Account destination, Account source) {
        destination.setCardNumber(source.getCardNumber());
        destination.setBank(source.getBank());
        destination.setPaymentSystem(source.getPaymentSystem());
    }

    private Account mapCreatingAccountDtoToAccount(CreatingAccountDto accountDto) {
        return Account.builder()
                .bank(Bank.builder()
                        .name(accountDto.getBankName())
                        .build())
                .cardNumber(accountDto.getCardNumber())
                .paymentSystem(accountDto.getPaymentSystem())
                .build();
    }

}

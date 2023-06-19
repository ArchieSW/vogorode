package dev.archie.handymanservice.handyman;

import dev.archie.handymanservice.account.Account;
import dev.archie.handymanservice.account.bank.Bank;
import dev.archie.handymanservice.account.dto.CreatingAccountDto;
import dev.archie.handymanservice.handyman.dto.CreatingHandymanDto;
import dev.archie.handymanservice.handyman.exception.NoSuchUserException;
import dev.archie.handymanservice.landscape.CreatingUserDto;
import dev.archie.handymanservice.landscape.LandscapeService;
import dev.archie.handymanservice.landscape.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class HandymanService {

    private static final long HANDYMAN_USER_TYPE_ID = 2;

    private final HandymanRepository handymanRepository;

    private final LandscapeService landscapeService;

    /**
     * @param creatingHandymanDto handyman dto create. Email should not exist
     * @return Created profile
     */
    public Handyman create(CreatingHandymanDto creatingHandymanDto) {
        CreatingUserDto creatingUserDto = mapCreatingHandymanDtoToUserOne(creatingHandymanDto);
        User createdUser = landscapeService.createUser(creatingUserDto);
        Handyman handyman = mapUserToHandyman(createdUser, creatingHandymanDto);
        updateAccountsIds(handyman);
        return handymanRepository.insert(handyman);
    }

    /**
     * @param id for existing handyman
     * @return found handyman
     */
    public Handyman getById(String id) {
        return handymanRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(id));
    }

    /**
     * @param id of existing profile
     * @param creatingHandymanDto of new fields. New email should not exist
     * @return Profile od updated handyman
     */
    public Handyman update(String id, CreatingHandymanDto creatingHandymanDto) {
        Handyman handyman = getById(id);
        CreatingUserDto creatingUserDto = mapCreatingHandymanDtoToUserOne(creatingHandymanDto);
        User updatedUser = landscapeService.update(handyman.getInnerId(), creatingUserDto);
        Handyman updatedHandyman = mapUserToHandyman(updatedUser, creatingHandymanDto);
        updatedHandyman.setId(handyman.getId());
        updateAccountsIds(updatedHandyman);
        return handymanRepository.save(updatedHandyman);
    }

    /**
     * @param id of existing handyman
     */
    public void delete(String id) {
        Handyman handyman = getById(id);
        landscapeService.delete(handyman.getInnerId());
        handymanRepository.delete(handyman);
    }

    public Handyman update(Handyman user) {
        updateAccountsIds(user);
        return handymanRepository.save(user);
    }

    private static void updateAccountsIds(Handyman handyman) {
        List<Account> accounts = handyman.getAccounts();
        IntStream.range(0, accounts.size())
                .forEach(i -> accounts.get(i).setId(i));
    }

    private CreatingUserDto mapCreatingHandymanDtoToUserOne(CreatingHandymanDto creatingHandymanDto) {
        return CreatingUserDto.builder()
                .email(creatingHandymanDto.getEmail())
                .login(creatingHandymanDto.getLogin())
                .longitude(creatingHandymanDto.getLongitude())
                .latitude(creatingHandymanDto.getLatitude())
                .phoneNumber(creatingHandymanDto.getPhoneNumber())
                .userTypeId(HANDYMAN_USER_TYPE_ID)
                .build();
    }

    private Handyman mapUserToHandyman(User createdUser, CreatingHandymanDto creatingHandymanDto) {
        return Handyman.builder()
                .longitude(createdUser.getLongitude())
                .latitude(createdUser.getLatitude())
                .innerId(createdUser.getId())
                .skills(creatingHandymanDto.getSkills())
                .firstName(creatingHandymanDto.getFirstName())
                .lastName(creatingHandymanDto.getLastName())
                .email(createdUser.getEmail())
                .phone(createdUser.getPhoneNumber())
                .accounts(creatingHandymanDto.getAccounts().stream()
                        .map(this::mapCreatingAccountDtoToAccount)
                        .toList())
                .build();
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

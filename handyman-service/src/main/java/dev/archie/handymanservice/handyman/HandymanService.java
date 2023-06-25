package dev.archie.handymanservice.handyman;

import dev.archie.handymanservice.account.Account;
import dev.archie.handymanservice.account.AccountService;
import dev.archie.handymanservice.handyman.dto.CreatingHandymanDto;
import dev.archie.handymanservice.handyman.exception.NoSuchUserException;
import dev.archie.handymanservice.handyman.skill.Skill;
import dev.archie.handymanservice.handyman.skill.SkillRepository;
import dev.archie.handymanservice.landscape.HandymanClient;
import dev.archie.handymanservice.landscape.dto.CreatingUserDto;
import dev.archie.handymanservice.landscape.LandscapeService;
import dev.archie.handymanservice.landscape.User;
import dev.archie.handymanservice.landscape.dto.HandymanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HandymanService {

    private static final long HANDYMAN_USER_TYPE_ID = 2;

    private final HandymanRepository handymanRepository;

    private final LandscapeService landscapeService;

    private final SkillRepository skillRepository;

    private final AccountService accountService;

    private final HandymanClient handymanClient;

    /**
     * @param creatingHandymanDto handyman dto create. Email should not exist
     * @return Created profile
     */
    public Handyman create(CreatingHandymanDto creatingHandymanDto) {
        HandymanDto handymanDto = handymanClient.create(creatingHandymanDto);

        try {
            List<Skill> skills = saveSkills(creatingHandymanDto);
            Handyman handyman = mapUserToHandyman(handymanDto, creatingHandymanDto);
            handyman.setInnerId(handymanDto.getId());
            handyman.setSkills(skills);
            handyman = handymanRepository.insert(handyman);

            String handymanId = handyman.getId();
            List<Account> accounts = saveAccounts(creatingHandymanDto, handymanId);
            handyman.setAccounts(accounts);
            return handymanRepository.save(handyman);
        } catch (Throwable anyException) {
            landscapeService.delete(handymanDto.getId());
            throw anyException;
        }
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
        HandymanDto updated = handymanClient.update(handyman.getInnerId(), creatingHandymanDto);
        Handyman updatedHandyman = mapUserToHandyman(updated, creatingHandymanDto);
        updatedHandyman.setId(handyman.getId());
        handymanRepository.save(updatedHandyman);
        List<Account> accounts = saveAccounts(creatingHandymanDto, updatedHandyman.getId());
        List<Skill> skills = saveSkills(creatingHandymanDto);
        updatedHandyman.setAccounts(accounts);
        updatedHandyman.setSkills(skills);
        return handymanRepository.save(updatedHandyman);
    }

    /**
     * @param id of existing handyman
     */
    public void delete(String id) {
        Handyman handyman = getById(id);
        handymanClient.delete(handyman.getInnerId());
        handymanRepository.delete(handyman);
    }

    private List<Skill> saveSkills(CreatingHandymanDto creatingHandymanDto) {
        return creatingHandymanDto.getSkills().stream()
                .map(skillName -> Skill.builder()
                        .name(skillName)
                        .build())
                .map(skillRepository::save)
                .toList();
    }

    private List<Account> saveAccounts(CreatingHandymanDto creatingHandymanDto, String handymanId) {
        return creatingHandymanDto.getAccounts().stream()
                .map(accountDto -> accountService.create(accountDto, handymanId))
                .toList();
    }

    private Handyman mapUserToHandyman(HandymanDto createdUser, CreatingHandymanDto creatingHandymanDto) {
        return Handyman.builder()
                .longitude(createdUser.getLongitude())
                .latitude(createdUser.getLatitude())
                .innerId(createdUser.getId())
                .firstName(creatingHandymanDto.getFirstName())
                .lastName(creatingHandymanDto.getLastName())
                .email(creatingHandymanDto.getEmail())
                .phone(creatingHandymanDto.getPhoneNumber())
                .build();
    }
}

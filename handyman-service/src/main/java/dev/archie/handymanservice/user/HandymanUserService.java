package dev.archie.handymanservice.user;

import dev.archie.handymanservice.account.Account;
import dev.archie.handymanservice.account.AccountService;
import dev.archie.handymanservice.account.dto.CreatingAccountDto;
import dev.archie.handymanservice.handyman.exception.NoSuchHandymanUserException;
import dev.archie.handymanservice.user.dto.CreatingHandymanUserDto;
import dev.archie.handymanservice.user.exception.HandymanUserAlreadyExistsException;
import dev.archie.handymanservice.user.skill.Skill;
import dev.archie.handymanservice.user.skill.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HandymanUserService {

    private final HandymanUserRepository handymanUserRepository;
    private final AccountService accountService;
    private final SkillRepository skillRepository;

    @Transactional
    public HandymanUser create(CreatingHandymanUserDto handymanUserDto) {
        HandymanUser user = mapCreatingHandymanUserDtoToHandymanUser(handymanUserDto);
        String email = user.getEmail();
        if (handymanUserRepository.existsByEmail(email)) {
            throw new HandymanUserAlreadyExistsException(email);
        }
        user = handymanUserRepository.save(user);
        List<Skill> skills = createSkills(handymanUserDto, user);
        user.setSkills(skills);
        List<Account> accounts = createAccounts(handymanUserDto, user);
        user.setAccounts(accounts);
        return handymanUserRepository.save(user);
    }

    public HandymanUser publishPhoto(Long id, MultipartFile photo) throws IOException {
        HandymanUser user = getById(id);
        user.setPhoto(photo.getBytes());
        return handymanUserRepository.save(user);
    }

    public HandymanUser getById(Long id) {
        return handymanUserRepository.findById(id)
                .orElseThrow(() -> new NoSuchHandymanUserException(id));
    }

    public HandymanUser update(CreatingHandymanUserDto handymanUserDto, Long id) {
        HandymanUser user = getById(id);
        String newEmail = handymanUserDto.getEmail();
        Optional<HandymanUser> possibleUserById = handymanUserRepository.findByEmail(newEmail);
        if (possibleUserById.isPresent() && !possibleUserById.get().equals(user)) {
            throw new HandymanUserAlreadyExistsException(newEmail);
        }
        user.setEmail(newEmail);
        user.setPhone(handymanUserDto.getPhone());
        user.setFirstName(handymanUserDto.getFirstName());
        user.setLastName(handymanUserDto.getLastName());
        return handymanUserRepository.save(user);
    }

    public void delete(Long id) {
        handymanUserRepository.deleteById(id);
    }

    private HandymanUser mapCreatingHandymanUserDtoToHandymanUser(CreatingHandymanUserDto handymanUserDto) {
        return HandymanUser.builder()
                .email(handymanUserDto.getEmail())
                .phone(handymanUserDto.getPhone())
                .firstName(handymanUserDto.getFirstName())
                .lastName(handymanUserDto.getLastName())
                .build();
    }

    private List<Skill> createSkills(CreatingHandymanUserDto handymanUserDto, HandymanUser user) {
        List<Skill> skills = new ArrayList<>();
        for (String skillName : handymanUserDto.getSkills()) {
            Skill skill = createSkill(user, skillName);
            skills.add(skill);
        }
        return skills;
    }

    private Skill createSkill(HandymanUser user, String skillName) {
        Skill skill = Skill.builder()
                .handymanUser(user)
                .name(skillName)
                .build();
        return skillRepository.save(skill);
    }

    private List<Account> createAccounts(CreatingHandymanUserDto handymanUserDto, HandymanUser user) {
        List<Account> accounts = new ArrayList<>();
        for (CreatingAccountDto accountDto : handymanUserDto.getAccounts()) {
            Account account = accountService.create(accountDto, user.getId());
            accounts.add(account);
        }
        return accounts;
    }
}

package dev.archie.handymanservice.handyman;

import dev.archie.handymanservice.account.Account;
import dev.archie.handymanservice.account.AccountService;
import dev.archie.handymanservice.handyman.dto.CreatingHandymanDto;
import dev.archie.handymanservice.handyman.exception.NoSuchUserException;
import dev.archie.handymanservice.handyman.skill.Skill;
import dev.archie.handymanservice.handyman.skill.SkillRepository;
import dev.archie.handymanservice.landscape.HandymanClient;
import dev.archie.handymanservice.landscape.LandscapeService;
import dev.archie.handymanservice.landscape.Order;
import dev.archie.handymanservice.landscape.OrderClient;
import dev.archie.handymanservice.landscape.WorkStatus;
import dev.archie.handymanservice.landscape.dto.CreatingOrderDto;
import dev.archie.handymanservice.landscape.dto.HandymanDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class HandymanService {

    private final HandymanRepository handymanRepository;

    private final LandscapeService landscapeService;

    private final SkillRepository skillRepository;

    private final AccountService accountService;

    private final HandymanClient handymanClient;
    private final OrderClient orderClient;

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
     * @param id                  of existing profile
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

    public boolean orderAJob(UUID innerId, Order order) {
        if (!handymanRepository.existsByInnerId(innerId)) {
            throw new NoSuchUserException(innerId.toString());
        }
        boolean acceptedTheJob = order.getSkills().stream()
                .noneMatch(skill -> skill.getName().contains("поливать"));
        if (acceptedTheJob) {
            CompletableFuture.runAsync(() -> getTheJobDone(order, innerId));
            log.info(innerId + " принял работу");
        } else {
            log.info(innerId + " отказался от работы");
        }
        return acceptedTheJob;
    }

    public void getTheJobDone(Order order, UUID innerId) {
        Random random = new Random();
        try {
            Thread.sleep(random.nextLong(4000) + 3000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Execution was interrupted" + e);
        }
        order.setStatus(WorkStatus.DONE);
        CreatingOrderDto updatingDto = CreatingOrderDto.builder()
                .status(order.getStatus())
                .fieldId(order.getField().getId())
                .userId(innerId)
                .workType(order.getWorkType())
                .skills(order.getSkills().stream().map(Skill::getName).toList())
                .build();
        orderClient.update(order.getId(), updatingDto);
        log.info(innerId + " выполнил работу");
    }

}

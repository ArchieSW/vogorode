package dev.archie.rancherservice.rancher;

import dev.archie.landscapeservice.order.dto.SendOrderToUser;
import dev.archie.rancherservice.landscape.GardenerClient;
import dev.archie.rancherservice.landscape.dto.CreatingOrderDto;
import dev.archie.rancherservice.landscape.dto.GardenerDto;
import dev.archie.rancherservice.landscape.dto.OrderDto;
import dev.archie.rancherservice.landscape.dto.WorkStatus;
import dev.archie.rancherservice.landscape.dto.WorkType;
import dev.archie.rancherservice.order.OrderService;
import dev.archie.rancherservice.rancher.dto.CreatingGardenerDto;
import dev.archie.rancherservice.rancher.exception.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class GardenerService {

    private final GardenerRepository gardenerRepository;
    private final GardenerClient gardenerClient;
    private final OrderService orderService;
    private final KafkaTemplate<String, SendOrderToUser> kafkaTemplate;

    /**
     * @param creatingGardenerDto rancher to create
     * @return created rancher's profile
     */
    public Gardener create(CreatingGardenerDto creatingGardenerDto) {
        GardenerDto gardenerDto = gardenerClient.create(creatingGardenerDto);
        Gardener gardener = mapGardenerDtoToGardener(creatingGardenerDto, gardenerDto);
        gardener.setInnerId(gardenerDto.getId());
        return gardenerRepository.save(gardener);
    }

    /**
     * @param id of existing rancher
     * @return found profile
     */
    public Gardener getById(String id) {
        return gardenerRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(id));
    }

    /**
     * @param id                  of existing rancher
     * @param creatingGardenerDto new fields. New email should not exist
     * @return updated profile
     */
    public Gardener update(String id, CreatingGardenerDto creatingGardenerDto) {
        Gardener gardener = getById(id);
        GardenerDto user = gardenerClient.update(gardener.getInnerId(), creatingGardenerDto);
        gardener = mapGardenerDtoToGardener(creatingGardenerDto, user);
        gardener.setId(gardener.getId());
        return gardenerRepository.save(gardener);
    }

    /**
     * @param id of existing user
     */
    public void delete(String id) {
        Gardener gardener = getById(id);
        gardenerClient.delete(gardener.getInnerId());
        gardenerRepository.delete(gardener);
    }

    private static Gardener mapGardenerDtoToGardener(CreatingGardenerDto creatingGardenerDto, GardenerDto user) {
        return Gardener.builder()
                .lastName(creatingGardenerDto.getLastName())
                .firstName(creatingGardenerDto.getFirstName())
                .phone(user.getPhoneNumber())
                .email(user.getEmail())
                .login(user.getLogin())
                .innerId(user.getId())
                .build();
    }

    public void notifyAboutMissingWorkers(OrderDto orderDto) {
        log.info("По данному запросу копателей не нашлось. Измените объем работ.");
        // по-хорошему тут нужно держать соединение с клиентом, но представим, что мы его уведомили
        CreatingOrderDto updatingOrderDto = CreatingOrderDto.builder()
                .fieldId(orderDto.getField().getId())
                .workType(WorkType.SHOVEL)
                .skills(List.of("сажать"))
                .build();
        orderService.update(updatingOrderDto, orderDto.getId());
    }

    public void notifyAboutOrderUpdate(OrderDto orderDto) {
        log.info("Order updated: " + orderDto);
        if (orderDto.getStatus().equals(WorkStatus.DONE)) {
            CompletableFuture.runAsync(() -> evaluateTheOrder(orderDto));
        }
    }

    public void evaluateTheOrder(OrderDto orderDto) {
        log.info("Оцениваем работу");
        Random random = new Random();
        orderDto.setGrade(random.nextInt(10));
        kafkaTemplate.send("landscape.order.rated", new SendOrderToUser(null, orderDto));
    }
}

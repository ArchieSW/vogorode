package dev.archie.landscapeservice.order;

import dev.archie.landscapeservice.account.skill.Skill;
import dev.archie.landscapeservice.field.Field;
import dev.archie.landscapeservice.gardener.GardenerClient;
import dev.archie.landscapeservice.handyman.Handyman;
import dev.archie.landscapeservice.handyman.HandymanClient;
import dev.archie.landscapeservice.handyman.HandymanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderResolver {

    private final OrderRepository orderRepository;
    private final HandymanRepository handymanRepository;
    private final HandymanClient handymanClient;
    private final GardenerClient gardenerClient;

    @Transactional
    @Scheduled(initialDelay = 5000, fixedDelay = 2000)
    public void sceduledResolver() {
        resolve();
    }

    @Transactional
    public void resolve() {
        Optional<Order> firstCreatedOrder = orderRepository.findFirstCreatedOrder();
        if (firstCreatedOrder.isEmpty()) {
            return;
        }
        Order order = firstCreatedOrder.get();

        List<Handyman> threeNearestSuitableHandymen = findThreeNearestSuitableHandymen(order);
        if (threeNearestSuitableHandymen.size() == 3) {
            log.info("notifying handymen");
            notifyFoundHandymen(threeNearestSuitableHandymen, order);
        } else {
            log.info("notifying rancher");
            notifyRancher(order);
        }
    }

    private List<Handyman> findThreeNearestSuitableHandymen(Order order) {
        Field field = order.getField();
        Set<Skill> requiredSkills = order.getSkills();
        List<Handyman> foundHandymen = new ArrayList<>();
        List<Handyman> nearestNotBusyHandyman = handymanRepository.findNearestNotBusyHandyman(field.getLatitude(), field.getLongitude());
        nearestNotBusyHandyman.forEach(handyman -> {
            if (requiredSkills.containsAll(handyman.getSkills())) {
                foundHandymen.add(handyman);
            }
        });
        return foundHandymen.stream().limit(3).toList();
    }

    private void notifyFoundHandymen(List<Handyman> threeNearestSuitableHandymen, Order order) {
        if (threeNearestSuitableHandymen.stream().allMatch(handyman -> handymanClient.orderAJob(handyman.getId(), order))) {
            order.setStatus(WorkStatus.APPROVED);
        }
    }

    private void notifyRancher(Order order) {
        gardenerClient.notifyAboutMissingWorkers(order);
    }

}

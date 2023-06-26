package dev.archie.landscapeservice.order;

import dev.archie.landscapeservice.account.skill.Skill;
import dev.archie.landscapeservice.field.Field;
import dev.archie.landscapeservice.gardener.GardenerClient;
import dev.archie.landscapeservice.handyman.Handyman;
import dev.archie.landscapeservice.handyman.HandymanRepository;
import dev.archie.landscapeservice.order.dto.CreatingOrderDto;
import dev.archie.landscapeservice.order.dto.SendOrderToUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderResolver {
    private final OrderRepository orderRepository;

    private final HandymanRepository handymanRepository;
    private final KafkaTemplate<String, SendOrderToUser> kafkaTemplate;
    private final OrderService orderService;

    @KafkaListener(topics = "landscape.resolve.order")
    @Transactional
    public void sceduledResolver(Order order) {
        resolve(order);
    }

    @Transactional
    public void resolve(Order order) {
        List<Handyman> threeNearestSuitableHandymen = findThreeNearestSuitableHandymen(order);
        if (threeNearestSuitableHandymen.size() == 3) {
            log.info("notifying handymen");
            notifyFoundHandymen(threeNearestSuitableHandymen, order);
        } else {
            log.info("notifying rancher");
            notifyRancher(order);
        }
    }

    @Transactional
    public List<Handyman> findThreeNearestSuitableHandymen(Order order) {
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

    @KafkaListener(topics = "landscape.order.complete")
    public void getTheJobDone(SendOrderToUser orderToUser) {
        Order order = orderToUser.order();
        order.setStatus(WorkStatus.DONE);
        CreatingOrderDto updating = CreatingOrderDto.builder()
                .fieldId(order.getField().getId())
                .workType(order.getWorkType())
                .status(order.getStatus())
                .skills(order.getSkills().stream().map(Skill::getName).toList())
                .build();
        orderService.update(updating, order.getId());
    }

    @KafkaListener(topics = "landscape.order.rated")
    public void saveRatedOrder(SendOrderToUser orderToUser) {
        Order order = orderToUser.order();
        order.setStatus(WorkStatus.APPROVED);
        CreatingOrderDto updating = CreatingOrderDto.builder()
                .fieldId(order.getField().getId())
                .workType(order.getWorkType())
                .status(order.getStatus())
                .skills(order.getSkills().stream().map(Skill::getName).toList())
                .grade(order.getGrade())
                .userId(order.getUser().getId())
                .build();
        orderService.update(updating, order.getId());
    }

    private void notifyFoundHandymen(List<Handyman> threeNearestSuitableHandymen, Order order) {
        for (Handyman handyman : threeNearestSuitableHandymen) {
            kafkaTemplate.send("handyman.notify.order", new SendOrderToUser(handyman, order));
        }
    }

    private void notifyRancher(Order order) {
        kafkaTemplate.send("rancher.notify.missing-workers", new SendOrderToUser(order.getField().getGardener(), order));
    }

}

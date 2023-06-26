package dev.archie.rancherservice.rancher;

import dev.archie.landscapeservice.order.dto.SendOrderToUser;
import dev.archie.rancherservice.rancher.dto.CreatingGardenerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gardeners")
public class GardenerController {

    private final GardenerService gardenerService;

    /**
     * @param creatingGardenerDto rancher to create
     * @return created rancher's profile
     */
    @PostMapping
    public Gardener create(@RequestBody CreatingGardenerDto creatingGardenerDto) {
        return gardenerService.create(creatingGardenerDto);
    }

    /**
     * @param id of existing rancher
     * @return found profile
     */
    @GetMapping("/{id}")
    public Gardener getById(@PathVariable String id) {
        return gardenerService.getById(id);
    }

    /**
     * @param id of existing rancher
     * @param creatingGardenerDto new fields. New email should not exist
     * @return updated profile
     */
    @PutMapping("/{id}")
    public Gardener update(@PathVariable String id, @RequestBody CreatingGardenerDto creatingGardenerDto) {
        return gardenerService.update(id, creatingGardenerDto);
    }

    /**
     * @param id of existing user
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        gardenerService.delete(id);
    }

    @KafkaListener(topics = "rancher.notify.missing-workers")
    public void notifyAboutMissingWorkers(SendOrderToUser sendOrderToUser) {
        gardenerService.notifyAboutMissingWorkers(sendOrderToUser.order());
    }

    @KafkaListener(topics = "rancher.notify.order-updated")
    public void notifyAboutOrderUpdate(SendOrderToUser sendOrderToUser) {
        gardenerService.notifyAboutOrderUpdate(sendOrderToUser.order());
    }
}

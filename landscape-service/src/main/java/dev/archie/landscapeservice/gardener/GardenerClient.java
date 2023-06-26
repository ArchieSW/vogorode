package dev.archie.landscapeservice.gardener;

import dev.archie.landscapeservice.order.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "gardener-client", url = "${application.rancher.rest.url}", path = "/gardeners")
public interface GardenerClient {

    @PostMapping("/notify/missingWorkers")
    void notifyAboutMissingWorkers(@RequestBody Order order);

    @PostMapping("/notify/orderUpdate")
    void notifyAboutOrderUpdate(@RequestBody Order orderDto);
}

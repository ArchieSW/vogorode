package dev.archie.landscapeservice.gardener;

import dev.archie.landscapeservice.order.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gardener-client", url = "${application.rancher.rest.url}", path = "/gardeners")
public interface GardenerClient {

    @PostMapping("/notify/missingWorkers")
    void notifyAboutMissingWorkers(@RequestBody Order order);

    @PostMapping("/notify/orderUpdate")
    void notifyAboutOrderUpdate(@RequestBody Order orderDto);
}

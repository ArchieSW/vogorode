package dev.archie.landscapeservice.handyman;

import dev.archie.landscapeservice.order.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(url = "${application.handyman.rest.url}", path = "/handymen", name = "handyman-client")
public interface HandymanClient {
    @PostMapping("/order/{innerId}")
    boolean orderAJob(@PathVariable UUID innerId, @RequestBody Order order);
}

package dev.archie.handymanservice.landscape;

import dev.archie.handymanservice.landscape.dto.CreatingOrderDto;
import dev.archie.handymanservice.landscape.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-client", path = "/orders", url = "${application.landscape.rest.url}")
public interface OrderClient {

    @PostMapping
    OrderDto create(@RequestBody CreatingOrderDto orderDto);

    @GetMapping("/{id}")
    OrderDto getById(@PathVariable Long id);
    @PutMapping("/{id}")
    OrderDto update(@PathVariable Long id, @RequestBody CreatingOrderDto orderDto);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

}


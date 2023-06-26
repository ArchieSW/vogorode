package dev.archie.rancherservice.landscape;

import dev.archie.rancherservice.landscape.dto.CreatingOrderDto;
import dev.archie.rancherservice.landscape.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "order-client", path = "/orders", url = "${application.landscape.rest.url}")
public interface OrderClient {

    @PostMapping
    OrderDto create(@RequestBody CreatingOrderDto orderDto);

    @GetMapping("/{id}")
    OrderDto getById(@PathVariable Long id);

    @GetMapping
    Page<OrderDto> getAllSorted(@RequestParam(name = "page") int pageNumber,
                                @RequestParam(name = "size") int size,
                                @RequestParam(name = "id") UUID userId,
                                @RequestParam(name = "direction", required = false) String direction);

    @PutMapping("/{id}")
    OrderDto update(@PathVariable Long id, @RequestBody CreatingOrderDto orderDto);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

}


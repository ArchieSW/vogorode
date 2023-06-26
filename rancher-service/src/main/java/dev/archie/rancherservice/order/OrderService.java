package dev.archie.rancherservice.order;

import dev.archie.rancherservice.landscape.OrderClient;
import dev.archie.rancherservice.landscape.dto.CreatingOrderDto;
import dev.archie.rancherservice.landscape.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderClient orderClient;

    public OrderDto create(CreatingOrderDto creatingOrderDto) {
        return orderClient.create(creatingOrderDto);
    }

    public OrderDto update(CreatingOrderDto creatingOrderDto, Long id) {
        return orderClient.update(id, creatingOrderDto);
    }

    public OrderDto getById(Long id) {
        return orderClient.getById(id);
    }

    public void delete(Long id) {
        orderClient.delete(id);
    }
}

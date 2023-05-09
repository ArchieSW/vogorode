package dev.archie.landscapeservice.order;

import dev.archie.landscapeservice.order.dto.CreatingOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order create(@RequestBody CreatingOrderDto orderDto) {
        return orderService.create(orderDto);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @GetMapping
    public Page<Order> getAllSorted(@RequestParam(name = "page") int pageNumber,
                                    @RequestParam(name = "size") int size,
                                    @RequestParam(name = "id") UUID userId,
                                    @RequestParam(name = "direction", required = false) String direction) {
        return orderService.getAllSorted(pageNumber, size, userId, direction);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody CreatingOrderDto orderDto) {
        return orderService.update(orderDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }

}

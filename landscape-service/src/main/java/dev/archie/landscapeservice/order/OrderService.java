package dev.archie.landscapeservice.order;

import dev.archie.landscapeservice.field.Field;
import dev.archie.landscapeservice.field.FieldService;
import dev.archie.landscapeservice.order.dto.CreatingOrderDto;
import dev.archie.landscapeservice.order.exception.DirectionIsNotSpecifiedException;
import dev.archie.landscapeservice.order.exception.NoSuchOrderException;
import dev.archie.landscapeservice.user.User;
import dev.archie.landscapeservice.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    public static final String DEFAULT_DIRECTION = "ASC";

    private final UserService userService;
    private final FieldService fieldService;
    private final OrderRepository orderRepository;

    public Order create(CreatingOrderDto orderDto) {
        User user = userService.getById(orderDto.getUserId());
        Field field = fieldService.getById(orderDto.getFieldId());
        Order order = mapCreatingOrderDtoToOrder(orderDto, user, field);
        return orderRepository.save(order);
    }

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchOrderException(id));
    }

    public Page<Order> getAllSorted(int pageNumber, int size, UUID userId, String direction) {
        if (direction == null) {
            direction = DEFAULT_DIRECTION;
        }
        PageRequest request = PageRequest.of(pageNumber, size);
        return switch (direction) {
            case "ASC" -> orderRepository.findByUser_IdOrderByUser_LoginAsc(userId, request);
            case "DESC" -> orderRepository.findByUser_IdOrderByUser_LoginDesc(userId, request);
            default -> throw new DirectionIsNotSpecifiedException(direction);
        };
    }

    public Order update(CreatingOrderDto creatingOrderDto, Long id) {
        Order order = getById(id);
        order.setStatus(creatingOrderDto.getStatus());
        order.setWorkType(creatingOrderDto.getWorkType());
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        Order order = getById(id);
        orderRepository.delete(order);
    }

    private static Order mapCreatingOrderDtoToOrder(CreatingOrderDto orderDto, User user, Field field) {
        return Order.builder()
                .createdAt(LocalDateTime.now())
                .status(orderDto.getStatus())
                .workType(orderDto.getWorkType())
                .field(field)
                .user(user)
                .build();
    }
}

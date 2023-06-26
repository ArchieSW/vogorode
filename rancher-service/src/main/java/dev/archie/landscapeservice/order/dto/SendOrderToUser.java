package dev.archie.landscapeservice.order.dto;


import dev.archie.rancherservice.landscape.User;
import dev.archie.rancherservice.landscape.dto.OrderDto;

public record SendOrderToUser(
        User handyman,
        OrderDto order
) {
}

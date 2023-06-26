package dev.archie.landscapeservice.order.dto;

import dev.archie.landscapeservice.order.Order;
import dev.archie.landscapeservice.user.User;

public record SendOrderToUser(
        User handyman,
        Order order
) {
}

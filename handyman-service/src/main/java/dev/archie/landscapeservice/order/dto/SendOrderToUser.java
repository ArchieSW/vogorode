package dev.archie.landscapeservice.order.dto;

import dev.archie.handymanservice.landscape.Order;
import dev.archie.handymanservice.landscape.User;

public record SendOrderToUser(
        User handyman,
        Order order
) {
}

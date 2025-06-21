package com.codingshuttle.ecommerce.inventory_service.dto;

import com.codingshuttle.ecommerce.inventory_service.entities.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrdersRequestDto {
    private Long id;
    private OrderStatus orderStatus;
    private Double totalPrice;
    private List<OrderRequestItemDto> orderItems;
}

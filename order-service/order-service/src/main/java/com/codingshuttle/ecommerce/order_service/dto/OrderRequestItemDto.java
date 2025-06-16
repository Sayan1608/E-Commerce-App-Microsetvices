package com.codingshuttle.ecommerce.order_service.dto;

import com.codingshuttle.ecommerce.order_service.entities.Orders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderRequestItemDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    @JsonIgnore
    private OrdersRequestDto order;
}

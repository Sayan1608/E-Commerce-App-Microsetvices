package com.codingshuttle.ecommerce.order_service.dto;

import com.codingshuttle.ecommerce.order_service.entities.OrderItem;
import com.codingshuttle.ecommerce.order_service.entities.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class OrdersRequestDto {
    private Long id;
    private OrderStatus orderStatus;
    private Double totalPrice;
    private List<OrderRequestItemDto> orderItems;
}

package com.codingshuttle.ecommerce.order_service.services;

import com.codingshuttle.ecommerce.order_service.dto.OrdersRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrdersService {
    List<OrdersRequestDto> findAllOrders();

    OrdersRequestDto findOrderById(Long orderId);
}

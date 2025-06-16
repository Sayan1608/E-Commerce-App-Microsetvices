package com.codingshuttle.ecommerce.order_service.services;

import com.codingshuttle.ecommerce.order_service.dto.OrdersRequestDto;
import com.codingshuttle.ecommerce.order_service.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<OrdersRequestDto> findAllOrders() {
        return ordersRepository
                .findAll()
                .stream()
                .map(order1 -> modelMapper.map(order1, OrdersRequestDto.class))
                .toList();
    }

    @Override
    public OrdersRequestDto findOrderById(Long orderId) {
        return ordersRepository
                .findById(orderId)
                .map(order1 -> modelMapper.map(order1, OrdersRequestDto.class))
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
    }
}

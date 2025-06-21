package com.codingshuttle.ecommerce.order_service.services;

import com.codingshuttle.ecommerce.order_service.clients.InventoryFeignClient;
import com.codingshuttle.ecommerce.order_service.dto.OrderRequestItemDto;
import com.codingshuttle.ecommerce.order_service.dto.OrdersRequestDto;
import com.codingshuttle.ecommerce.order_service.entities.OrderItem;
import com.codingshuttle.ecommerce.order_service.entities.OrderStatus;
import com.codingshuttle.ecommerce.order_service.entities.Orders;
import com.codingshuttle.ecommerce.order_service.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;
    private final InventoryFeignClient inventoryFeignClient;


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

    @Override
    public OrdersRequestDto createNewOrder(OrdersRequestDto ordersRequestDto) {
        Double totalPrice = inventoryFeignClient.reduceStock(ordersRequestDto);
        ordersRequestDto.setTotalPrice(totalPrice);
        ordersRequestDto.setOrderStatus(OrderStatus.CONFIRMED);
        Orders savedOrder = ordersRepository.save(modelMapper.map(ordersRequestDto, Orders.class));
        for(OrderItem item : savedOrder.getOrderItems()){
            item.setOrder(savedOrder);
        }
        return modelMapper.map(savedOrder,OrdersRequestDto.class);
    }
}

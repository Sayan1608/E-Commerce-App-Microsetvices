package com.codingshuttle.ecommerce.order_service.services;

import com.codingshuttle.ecommerce.order_service.clients.InventoryFeignClient;
import com.codingshuttle.ecommerce.order_service.dto.OrderRequestItemDto;
import com.codingshuttle.ecommerce.order_service.dto.OrdersRequestDto;
import com.codingshuttle.ecommerce.order_service.entities.OrderItem;
import com.codingshuttle.ecommerce.order_service.entities.OrderStatus;
import com.codingshuttle.ecommerce.order_service.entities.Orders;
import com.codingshuttle.ecommerce.order_service.repositories.OrderItemRepository;
import com.codingshuttle.ecommerce.order_service.repositories.OrdersRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;
    private final InventoryFeignClient inventoryFeignClient;
    private final OrderItemRepository orderItemRepository;


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
        Orders orders = ordersRepository.findById(orderId).get();
        return ordersRepository
                .findById(orderId)
                .map(order1 -> modelMapper.map(order1, OrdersRequestDto.class))
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
    }

    //@Retry(name = "inventoryRetry", fallbackMethod = "createNewOrderFallback")
    //@RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createNewOrderFallback")
    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createNewOrderFallback")
    @Override
    public OrdersRequestDto createNewOrder(OrdersRequestDto ordersRequestDto) {
        log.info("Calling createNewOrder method");
        Double totalPrice = inventoryFeignClient.reduceStock(ordersRequestDto);
        ordersRequestDto.setTotalPrice(totalPrice);
        ordersRequestDto.setOrderStatus(OrderStatus.CONFIRMED);
        Orders savedOrder = ordersRepository.save(modelMapper.map(ordersRequestDto, Orders.class));
        for(OrderItem item : savedOrder.getOrderItems()){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        return modelMapper.map(savedOrder,OrdersRequestDto.class);
    }

    @Override
    public OrdersRequestDto cancelOrder(Long id) {
        Orders bookedOrder = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id : " + id));
        OrdersRequestDto ordersRequestDto = modelMapper.map(bookedOrder, OrdersRequestDto.class);
        Double totalPrice = inventoryFeignClient.addStock(ordersRequestDto);
        ordersRequestDto.setOrderStatus(OrderStatus.CANCELLED);
        Orders savedOrder = ordersRepository.save(modelMapper.map(ordersRequestDto, Orders.class));
        for(OrderItem item : savedOrder.getOrderItems()){
            item.setOrder(savedOrder);
        }
        return modelMapper.map(savedOrder,OrdersRequestDto.class);
    }

    public OrdersRequestDto createNewOrderFallback(OrdersRequestDto ordersRequestDto, Throwable throwable) {
        log.error("Fallback occurred due to "+throwable.getMessage());
        return new OrdersRequestDto();
    }
}

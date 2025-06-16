package com.codingshuttle.ecommerce.order_service.controllers;

import com.codingshuttle.ecommerce.order_service.dto.OrdersRequestDto;
import com.codingshuttle.ecommerce.order_service.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/core")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;


    @GetMapping
    public ResponseEntity<List<OrdersRequestDto>> getAllOrders() {
        List<OrdersRequestDto> allOrders = ordersService.findAllOrders();
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping(path = "/{orderId}")
    public ResponseEntity<OrdersRequestDto> getOrderById(@PathVariable(name = "orderId") Long orderId) {
        OrdersRequestDto orderById = ordersService.findOrderById(orderId);
        return ResponseEntity.ok(orderById);
    }

    @GetMapping(path = "/helloOrders")
    public ResponseEntity<String> helloOrders(){
        return ResponseEntity.ok("Hello from Orders Service");
    }
}

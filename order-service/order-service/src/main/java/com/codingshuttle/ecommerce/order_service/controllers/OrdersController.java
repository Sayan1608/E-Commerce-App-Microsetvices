package com.codingshuttle.ecommerce.order_service.controllers;

import com.codingshuttle.ecommerce.order_service.dto.OrdersRequestDto;
import com.codingshuttle.ecommerce.order_service.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/create-order")
    public ResponseEntity<OrdersRequestDto> createNewOrder(@RequestBody OrdersRequestDto ordersRequestDto){
        OrdersRequestDto ordersRequestDto1 = ordersService.createNewOrder(ordersRequestDto);
        return new ResponseEntity<>(ordersRequestDto1,HttpStatus.CREATED);
    }

    @PutMapping(path = "/cancel-order/{orderId}")
    public ResponseEntity<OrdersRequestDto> cancelOrder(@PathVariable(name = "orderId") Long id){
        OrdersRequestDto ordersRequestDto=ordersService.cancelOrder(id);
        return ResponseEntity.ok(ordersRequestDto);
    }
}

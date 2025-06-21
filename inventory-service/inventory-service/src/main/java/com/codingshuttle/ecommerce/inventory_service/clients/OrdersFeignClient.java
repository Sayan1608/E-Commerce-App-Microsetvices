package com.codingshuttle.ecommerce.inventory_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-service", path = "/orders")
public interface OrdersFeignClient {
    @GetMapping(path = "/core/helloOrders")
    public ResponseEntity<String> helloOrders();
}

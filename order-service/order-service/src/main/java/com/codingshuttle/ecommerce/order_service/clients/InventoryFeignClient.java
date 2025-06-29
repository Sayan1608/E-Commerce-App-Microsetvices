package com.codingshuttle.ecommerce.order_service.clients;

import com.codingshuttle.ecommerce.order_service.dto.OrdersRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service",path = "/inventory")
public interface InventoryFeignClient {
    @PutMapping(path = "/products/reduce-stock")
    public Double reduceStock(@RequestBody OrdersRequestDto ordersRequestDto);

    @PutMapping(path = "/products/add-stock")
    public Double addStock(@RequestBody OrdersRequestDto ordersRequestDto);
}

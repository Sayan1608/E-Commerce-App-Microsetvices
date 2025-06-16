package com.codingshuttle.ecommerce.inventory_service.controllers;

import com.codingshuttle.ecommerce.inventory_service.dto.ProductDto;
import com.codingshuttle.ecommerce.inventory_service.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> allProductDetails = productService.getAllProductDetails();
        return ResponseEntity.ok(allProductDetails);
    }

    @GetMapping(path = "/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        ProductDto productById = productService.getProductById(productId);
        return ResponseEntity.ok(productById);
    }

    @GetMapping(path = "/fetchOrderService")
    public ResponseEntity<String> fetchOrders(){
        ServiceInstance orderService = discoveryClient.getInstances("order-service").get(0);

        String response = restClient
                .get()
                .uri(orderService.getUri() + "/orders/core/helloOrders")
                .retrieve()
                .body(String.class);
        return ResponseEntity.ok(response);
    }

}

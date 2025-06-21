package com.codingshuttle.ecommerce.inventory_service.service;

import com.codingshuttle.ecommerce.inventory_service.dto.OrdersRequestDto;
import com.codingshuttle.ecommerce.inventory_service.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProductDetails();
    ProductDto getProductById(Long productId);

    Double reduceStock(OrdersRequestDto ordersRequestDto);
}

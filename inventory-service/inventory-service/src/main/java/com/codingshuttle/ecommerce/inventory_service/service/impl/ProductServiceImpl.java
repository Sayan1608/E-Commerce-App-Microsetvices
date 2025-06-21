package com.codingshuttle.ecommerce.inventory_service.service.impl;

import com.codingshuttle.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.codingshuttle.ecommerce.inventory_service.dto.OrdersRequestDto;
import com.codingshuttle.ecommerce.inventory_service.dto.ProductDto;
import com.codingshuttle.ecommerce.inventory_service.entities.Product;
import com.codingshuttle.ecommerce.inventory_service.repository.ProductRepository;
import com.codingshuttle.ecommerce.inventory_service.service.ProductService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductDto> getAllProductDetails() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts
                .stream()
                .map(product ->modelMapper.map(product, ProductDto.class))
                .toList();
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional
                .map(product->modelMapper.map(product, ProductDto.class))
                .orElseThrow(()->new RuntimeException("Product Not Found With Id : " + productId));
    }

    @Override
    @Transactional
    public Double reduceStock(OrdersRequestDto ordersRequestDto) {
        log.info("Inside reduceStock method in ProductServiceImpl");
       double totalPrice = 0.0;
       for(OrderRequestItemDto orderRequestItemDto : ordersRequestDto.getOrderItems()){
           Long productId = orderRequestItemDto.getProductId();
           Integer quantity = orderRequestItemDto.getQuantity();
           Product product = productRepository.findById(productId).orElseThrow(() ->
                   new RuntimeException("Product not found with id: " + productId));

           if(product.getStock() < quantity){
               throw new RuntimeException("Insufficient quantity for product with id " + productId);
           }
           product.setStock(product.getStock() - quantity);
           productRepository.save(product);
           totalPrice+=quantity*product.getPrice();
           log.info("Inside reduceStock method in ProductServiceImpl,totalPrice=" + totalPrice);
       }
        log.info("Returning from reduceStock method in ProductServiceImpl,totalPrice=" + totalPrice);
        return totalPrice;

    }
}

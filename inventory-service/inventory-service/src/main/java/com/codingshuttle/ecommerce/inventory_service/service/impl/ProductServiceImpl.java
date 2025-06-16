package com.codingshuttle.ecommerce.inventory_service.service.impl;

import com.codingshuttle.ecommerce.inventory_service.dto.ProductDto;
import com.codingshuttle.ecommerce.inventory_service.entities.Product;
import com.codingshuttle.ecommerce.inventory_service.repository.ProductRepository;
import com.codingshuttle.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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
}

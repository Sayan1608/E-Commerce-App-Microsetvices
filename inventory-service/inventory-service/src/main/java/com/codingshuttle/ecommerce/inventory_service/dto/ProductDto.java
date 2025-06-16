package com.codingshuttle.ecommerce.inventory_service.dto;

import lombok.*;

@Data
public class ProductDto {

    private Long id;
    private String title;
    private Double price;
    private Integer stock;



}

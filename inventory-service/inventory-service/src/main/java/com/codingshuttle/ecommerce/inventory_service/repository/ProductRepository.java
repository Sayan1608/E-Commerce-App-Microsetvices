package com.codingshuttle.ecommerce.inventory_service.repository;

import com.codingshuttle.ecommerce.inventory_service.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}

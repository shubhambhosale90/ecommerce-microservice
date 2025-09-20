package com.smasher.ecommerce.inventory_service.repository;

import com.smasher.ecommerce.inventory_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProductRepository extends JpaRepository<Product, Long> {
}

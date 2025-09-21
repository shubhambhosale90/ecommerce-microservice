package com.smasher.ecommerce.inventory_service.controller;

import com.smasher.ecommerce.inventory_service.clients.OrdersFeignClient;
import com.smasher.ecommerce.inventory_service.dto.OrderRequestDto;
import com.smasher.ecommerce.inventory_service.dto.ProductDto;
import com.smasher.ecommerce.inventory_service.serrvice.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;
    private final OrdersFeignClient ordersFeignClient;

    @GetMapping("/fetchOrders")
    public String fetchFromOrderService() {
        return ordersFeignClient.helloOrders();
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllInventory() {
        List<ProductDto> inventories = productService.getAllInventory();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("reduce-stock")
    public ResponseEntity<Double> reduceStock(@RequestBody OrderRequestDto orderRequestDto) {
        Double totalPrice = productService.reduceStock(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }

}
